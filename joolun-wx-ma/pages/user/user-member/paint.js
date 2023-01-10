export default class LastMayday {
  palette(params) {
    return ({
      "width": "430px",
      "height": "460px",
      "background": "#f8f8f8",
      "views": [
        {
          "type": "image",
          "url": params.qrcode,
          "css": {
            "width": "430px",
            "height": "430px",
            "top": "0",
            "left": "0",
            "rotate": "0",
            "borderRadius": "",
            "borderWidth": "",
            "borderColor": "#000000",
            "shadow": "",
            "mode": "scaleToFill"
          }
        },
        {
          "type": "text",
          "text": params.nickName,
          "css": {
            "color": "#ffffff",
            "width": "280px",
            "top": "440px",
            "left": "200px",
            "fontSize": "22px",
            "fontWeight": "normal",
          }
        }
      ]
    });
  }
}