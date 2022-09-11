export default class LastMayday {
  palette(params) {
    return ({
      "width": "400px",
      "height": "1000px",
      "background": "#f8f8f8",
      "views": [{
          "type": "image",
          "url": "https://mall-owen.oss-cn-beijing.aliyuncs.com/vip1662865918661.jpg",
          "css": {
            "width": "400px",
            "height": "1000px",
            "top": "0px",
            "left": "0px",
            "rotate": "0",
            "borderRadius": "",
            "borderWidth": "",
            "borderColor": "#000000",
            "shadow": "",
            "mode": "scaleToFill"
          }
        },
        {
          "type": "image",
          "url": params.qrcode,
          "css": {
            "width": "100px",
            "height": "100px",
            "top": "859px",
            "left": "282px",
            "rotate": "0",
            "borderRadius": "",
            "borderWidth": "",
            "borderColor": "#000000",
            "shadow": "",
            "mode": "scaleToFill"
          }
        }
      ]
    });
  }
}