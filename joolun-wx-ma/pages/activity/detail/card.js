export default class LastMayday {
  palette(params) {
    return ({
      "width": "480px",
      "height": "710px",
      "background": "#FFFFFF",
      "views": [{
          "type": "image",
          "url": params.qrCode,
          "css": {
            "width": "115px",
            "height": "115px",
            "top": "540px",
            "left": "10px",
            "rotate": "0",
            "borderRadius": "5.78125px",
            "mode": "scaleToFill"
          }
        }, {
          "type": "image",
          "url": params.activityImage,
          "css": {
            "width": "480px",
            "height": "480px",
            "top": "0px",
            "left": "0px",
            "rotate": "0",
            "borderRadius": "10px",
            "borderWidth": "",
            "borderColor": "#000000",
            "shadow": "10 10 10 #888888",
            "mode": "scaleToFill"
          }
        },
        {
          "type": "text",
          "text": params.activityName,
          "css": {
            "color": "#707070",
            "width": "210px",
            "top": "490px",
            "left": "20px",
            "fontSize": "40rpx",
            "fontWeight": 500,
          }
        },
        {
          "type": "text",
          "text": params.price,
          "css": {
            "color": "#FF5133",
            "width": "210px",
            "top": "490px",
            "left": "400px",
            "fontSize": "40rpx",
            "fontWeight": 500,
          }
        },
        {
          "type": "text",
          "text": "① 长按图片分享好友",
          "css": {
            "color": "#848484",
            "width": "210px",
            "top": "570px",
            "left": "150px",
            "fontSize": "30rpx",
            "fontWeight": 500,
          }
        },
        {
          "type": "text",
          "text": "② 打开微信扫码可见",
          "css": {
            "color": "#848484",
            "width": "210px",
            "top": "590px",
            "left": "150px",
            "fontSize": "30rpx",
            "fontWeight": 500,
          }
        },
        {
          "type": "image",
          "url": params.logoImagePath,
          "css": {
            "width": "60px",
            "height": "60px",
            "top": "570px",
            "left": "410px",
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
            "color": "#BEBEBE",
            "width": "140px",
            "top": "660px",
            "left": "14px",
            "fontSize": "22px",
            "fontWeight": "normal",
          }
        }
      ]
    });
  }
}

const startTop = 50;
const startLeft = 20;
const gapSize = 70;
const common = {
  left: `${startLeft}rpx`,
  fontSize: '40rpx',
};

function _textDecoration(decoration, index, color) {
  return ({
    type: 'text',
    text: decoration,
    css: [{
      top: `${startTop + index * gapSize}rpx`,
      color: color,
      textDecoration: decoration,
    }, common],
  });
}

function _image(index, rotate, borderRadius) {
  return (
    {
      id: `image-${index}`,
      type: 'image',
      url: '/palette/avatar.jpg',
      css: {
        top: `${startTop + 8.5 * gapSize}rpx`,
        left: `${startLeft + 160 * index}rpx`,
        width: '120rpx',
        height: '120rpx',
        shadow: '10rpx 10rpx 5rpx #888888',
        rotate: rotate,
        minWidth: '60rpx',
        borderRadius: borderRadius,
        scalable: true,
      },
    }
  );
}

function _des(index, content) {
  const des = {
    type: 'text',
    text: content,
    css: {
      fontSize: '22rpx',
      top: `${startTop + 8.5 * gapSize + 140}rpx`,
    },
  };
  if (index === 3) {
    des.css.right = '60rpx';
  } else {
    des.css.left = `${startLeft + 120 * index + 30}rpx`;
  }
  return des;
}