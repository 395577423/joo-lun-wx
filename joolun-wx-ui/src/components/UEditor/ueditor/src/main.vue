<template>
  <div class="avue-ueditor">
    <div :id="id" />
    <div v-if="show"
         class="wangEditor_html-content">
      <div class="wangEditor_html-header">
        <span class="wangEditor_html-close"
              @click="show=false">x</span>
      </div>
      <textarea v-model="html" />
    </div>
  </div>
</template>
<script>
import { getClient } from './upload/ali'
import { getToken } from './upload/qiniu'
import { getObjValue, HTMLFormat, validatenull } from './upload/util'
import E from 'wangeditor'
export default {
  name: 'AvueUeditor',
  props: {
    options: {
      type: Object,
      default: () => {
        return {}
      },
    },
    id: {
      type: String,
      default: () => {
        return 'avue-ueditor-' + Math.ceil(Math.random() * 100)
      },
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    value: {
      type: String,
      default: '',
    },
    placeholder: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      text: '',
      html: '',
      show: false,
      editor: '',
    }
  },
  computed: {
    domId () {
      return '#' + this.id
    },
    imgFlag () {
      return this.img.url == ''
    },
    isImg () {
      return this.img.obj.src
    },
    domain () {
      return this.props.home || ''
    },
    urlKey () {
      return this.props.url || 'url'
    },
    props () {
      return this.options.props || {}
    },
    oss () {
      return this.options.oss
    },
    customConfig () {
      return this.options.customConfig || {}
    },
    action () {
      return this.options.action
    },
    qiniu () {
      return this.options.qiniu
    },
    ali () {
      return this.options.ali
    },
    isHtml () {
      return validatenull(this.customConfig.showHtml) ? true : this.customConfig.showHtml
    },
    isQiniuOSS () {
      return this.oss === 'qiniu'
    },
    isAliOSS () {
      return this.oss === 'ali'
    },
    headers () {
      return this.options.headers || {}
    },
  },
  watch: {
    disabled (val) {
      if (val) {
        this.editor.disable()
      } else {
        this.editor.enable()
      }
    },
    show (val) {
      if (val === false) {
        this.$emit('input', HTMLFormat(this.html))
        this.$emit('change', HTMLFormat(this.html))
      }
    },
    value (newVal) {
      if (this.editor) {
        if (newVal && newVal !== this.text) {
          this.text = newVal
          this.editor.txt.html(newVal)
        } else if (!newVal) {
          this.text = ''
          this.editor.txt.html('')
        }
      }
    },
  },
  mounted () {
    this.initEdit()
  },
  methods: {
    initEdit () {
      this.editor = new E(this.domId)
      window.wangEditor = E
      this.editor.config.zIndex = 100
      this.editor.config.menuTooltipPosition = 'down'
      this.editor.config.placeholder = this.placeholder
      this.editor.config.showFullScreen = true
      this.editor.config.onfocus = () => {
        this.$emit('focus', this.text)
      }
      this.editor.config.onblur = (html) => {
        this.$emit('blur', html)
      }
      this.editor.config.onchange = (html) => {
        this.text = html
        this.$emit('input', this.text)
        this.$emit('change', this.text)
      }
      this.editor.config.emotions = [
        {
          title: 'emoji',  // tab 的标题
          type: 'emoji', // 'emoji' / 'image'
          // emoji 表情，content 是一个数组即可
          content: '😀 😃 😄 😁 😆 😅 😂 😊 😇 🙂 🙃 😉 😓 😪 😴 🙄 🤔 😬 🤐'.split(/\s/),
        }
      ]
      this.initUploadImg()
      this.editor.config = Object.assign(
        this.editor.config,
        this.customConfig
      )
      this.editor.create()
      this.initPlugins()
      if (this.disabled === true) this.editor.disable()
      this.editor.txt.html(this.value)
    },
    initUploadImg () {
      this.editor.config.customUploadImg = (file, insert) => {
        this.uploadFile(file[0]).then((res) => {
          insert(res)
        })
      }
    },
    uploadFile (file) {
      return new Promise((resolve, reject) => {

        const headers = Object.assign(this.headers, {
          'Content-Type': 'multipart/form-data',
        })
        let oss_config = {}
        let client
        let param = new FormData()
        let url = this.action
        param.append('file', file)
        if (this.isQiniuOSS) {
          oss_config = this.qiniu
          const token = getToken(oss_config.AK, oss_config.SK, {
            scope: oss_config.scope,
            deadline:
              new Date().getTime() + oss_config.deadline * 3600,
          })
          param.append('token', token)
          url = 'http://up.qiniu.com/'
        } else if (this.isAliOSS) {
          oss_config = this.ali
          client = getClient({
            region: oss_config.region,
            endpoint: oss_config.endpoint,
            accessKeyId: oss_config.accessKeyId,
            accessKeySecret: oss_config.accessKeySecret,
            bucket: oss_config.bucket,
          })
        }
        (() => {
          if (this.isAliOSS) {
            return client.put(file.name, file)
          } else {
            return this.$axios.post(url, param, { headers })
          }
        })()
          .then((res) => {
            let list = {}
            var result = ''
            if (this.isAliOSS) {
              list = res
              result = list.url
            } else if (this.isQiniuOSS) {
              list = res.data
              list.key = oss_config.url + list.key
              result = list.key
            } else {
              list = getObjValue(
                res.data,
                this.props.res,
                'object'
              )
              result = this.domain + list[this.urlKey]
            }
            var html = result
            resolve(html)
          })
          .catch((err) => {
            reject(err)
          })
      })
    },
    getUEContent () {
      return this.editor.txt.html()
    },
    initPlugins () {
      if (this.isHtml) {
        E.views = {
          init: function (editorSelector) {
            document
              .querySelector(editorSelector + ' .w-e-toolbar')
              .appendHTML(
                `<div class="w-e-menu" data-title="源代码" onclick="window.wangEditor.views.toggleFullscreen()" >
                  <span class="wangEditor_html" >
                  Html
                  </span>
              </div>`
              )
          },
          toggleFullscreen: () => {
            this.html = HTMLFormat(this.value)
            this.show = true
          },
        }
        E.views.init(this.domId)
      }

    },
  },
}
</script>
<style>
.wangEditor_html {
  color: #999;
  font-size: 14px;
  font-weight: bold;
}
.wangEditor_html-content {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  z-index: 2048;
  padding: 0 20px 20px 20px;
  background-color: #fff;
  box-sizing: border-box;
}
.wangEditor_html-content textarea {
  width: 100%;
  height: 100%;
  border-color: #ccc;
  border-radius: 5px;
  padding: 5px;
}
.wangEditor_html-header {
  padding: 5px 0;
  text-align: right;
}
.wangEditor_html-close {
  color: #333;
  font-weight: bold;
  font-size: 22px;
}
</style>
