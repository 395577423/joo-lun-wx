let wxml = `
<view class="container">
  <view class="bg">
    <image class="img" src="https://mall-owen.oss-cn-beijing.aliyuncs.com/report2.png"></image>
  </view>
  <text class="useName">userNameText</text>
  <text class="challenge">challengeText</text>
  <text class="book">《bookText》</text>
  <text class="star">starText</text>
  <text class="duration">durationText</text>
</view>
`

const style = {
  container: {
    width: 300,
    height: 533,
  },
  duration:{
    width:50,
    height:20,
    position: 'absolute',
    top:178,
    left:120,
    fontSize:7,
    color:'red'
  },
  book:{
    width:80,
    height:20,
    position: 'absolute',
    top:159,
    left:125,
    fontSize:8,
    color:'red'
  },
  star:{
    width:30,
    height:20,
    position: 'absolute',
    top:104,
    left:142,
    fontSize:7
  },
  challenge:{
    width:30,
    height:20,
    position: 'absolute',
    top:95,
    left:140,
    fontSize:7
  },
  useName:{
    width:30,
    height:20,
    position: 'absolute',
    top:84,
    left:102,
    fontSize:7
  },
  img: {
    width: 300,
    height: 300,
    borderRadius: 10,
    position: 'absolute'
  }
}

module.exports = {
  wxml,
  style
}
