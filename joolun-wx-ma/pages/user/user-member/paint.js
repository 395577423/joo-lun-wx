export default class LastMayday {
  palette(params) {
    return ({
      "width": "400px",
      "height": "1000px",
      "background": "#f8f8f8",
      "views": [{
          "type": "image",
          "url": params.bgImage,
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
            "width": "80px",
            "height": "80px",
            "top": "900px",
            "left": "282px",
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
            "width": "140px",
            "top": "960px",
            "left": "222px",
            "fontSize": "22px",
            "fontWeight": "normal",
          }
        }
      ]
    });
  }
}