<avue-ueditor v-model="text"
              :options="options"></avue-ueditor>
<span class="avue-ueditor__code">{{text}}</span>
<script>
import avueUeditor from 'avue-plugin-ueditor'
export default {
  component:{
    avueUeditor
  },
  data() {
    return {
      text: '<h1 class="ql-align-center" style="text-align: center;"><a href="https://avuejs.com/doc/plugins/ueditor-plugins" target="_blank" style="font-weight: bold; color: rgb(194, 79, 74);">欢迎使用Avue富文本编辑器</a></h1><p class="ql-align-center" style="text-align: center;"><span style="font-weight: bold; color: rgb(194, 79, 74);"><img src="https://avuejs.com/images/logo-bg.jpg" height="200" width="200"></span></p>',
      options: {
        //普通图片上传
        action: "https://avuejs.com/imgupload",
        customConfig: {},//wangEditor编辑的配置
        props: {
          res: "data",
          url: "url"
        },
        //七牛云oss配置
        qiniu: {
          AK: "",
          SK: "",
          scope: "test",
          url: "http://pm7cc17lu.bkt.clouddn.com/",
          deadline: 1
        },
        //阿里云oss配置
        ali: {
          region: "oss-cn-beijing",
          endpoint: "oss-cn-beijing.aliyuncs.com",
          accessKeyId: "",
          accessKeySecret: "",
          bucket: "avue"
        }
      }
    };
  }
};
</script>
