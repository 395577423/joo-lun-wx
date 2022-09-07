export default class LastMayday {
  palette(params) {
    return ({
      "width": "480px",
      "height": "860px",
      "background": "#f8f8f8",
      "views": [{
          "type": "image",
          "url": "https://mall-owen.oss-cn-beijing.aliyuncs.com/report3.png",
          "css": {
            "width": "480px",
            "height": "860px",
            "top": "0px",
            "left": "0px",
            "rotate": "0",
            "borderColor": "#000000",
            "mode": "scaleToFill"
          }
        },
        {
          "type": "text",
          "text": params.nickName,
          "css": {
            "color": "#000000",
            "width": "200px",
            "height": "42px",
            "top": "237px",
            "left": "147px",
            "fontSize": "40rpx",
            "fontWeight": 500,
            "textAlign": "left"
          }
        },
        {
          "type": "text",
          "text": 5,
          "css": {
            "width": "30px",
            "height": "42px",
            "top": "270px",
            "left": "208px",
            "fontSize": "40rpx",
            "fontWeight": 500,
            "textAlign": "center"
          }
        },
        {
          "type": "text",
          "text": params.title,
          "css": {
            "color": "#000000",
            "width": "200px",
            "height": "42px",
            "top": "453px",
            "left": "183px",
            "fontSize": "40rpx",
            "fontWeight": 500,
            "textAlign": "left"
          }
        },
        {
          "type": "text",
          "text": "5分钟",
          "css": {
            "color": "#000000",

            "width": "90px",
            "height": "42px",
            "top": "510px",
            "left": "174px",
            "fontSize": "40rpx",
            "fontWeight": 500,
            "textAlign": "left"
          }
        },
        {
          "type": "image",
          "url": params.qrcode,
          "css": {
            "width": "100px",
            "height": "100px",
            "top": "620px",
            "left": "287px",
            "mode": "scaleToFill"
          }
        }
      ]
    });
  }
}