/**
 * Created by fangya on 2017/2/16.
 */
/**
 * 圆环进度
 * */
var Progress = function (pint) {
    if (pint > 100) {
        console.log('圆环进度超过100%，发生异常');
        return false;
    }
    var rotate = 45;
    var pw = $('.circleProgress_wrapper').parent().height() * 0.5;
    $('.circleProgress_wrapper').css({'height': pw, 'width': pw, 'margin-top': -pw * 0.65});
    $('.circleProgress').css({'width': pw - 10, 'height': pw - 10});
    $('.fontbox').html(pint).css('line-height', pw + 'px');
    if (pint > 50) {
        rotate = 225;
        rotate = (pint / 100) * 360 + rotate;
        $('.leftcircle').css('-webkit-transform', 'rotate(' + rotate + 'deg)');
        $('.rightcircle').css('-webkit-transform', 'rotate(225deg)');
    } else {
        rotate = (pint / 100) * 360 + rotate;
        $('.rightcircle').css('-webkit-transform', 'rotate(' + rotate + 'deg)');
    }
};