var data = {},
    _index = 0, //选择的第几个金额
    timer; //倒计时的定时器

$(function() {
    pageInit()
});

// 页面初始化
function pageInit() {
    $.get("http://47.104.142.76:8081/orderapi/odingbuying/init.action", function(res) {
        data = res.data;
        render(data)
      });
}

// 渲染数据
function render(data) {
    $('[data-key]').each(function (index, ele) {
        $(ele).html(data[$(ele).attr('data-key')])
    })
    for(var i = 0; i < data.cannGetAmount.length; i ++) {
        $('[data-amount]').eq(i).find('span').html(Object.keys(data.cannGetAmount[i])[0])
    }

    if(data.state == 0) {
        timeDownInit(data.ODIN_BUYING_TIME)
    } else {
        $('#await').hide()
        $('#hot').show()
        $('#progress').show()
        $('#progress-percent-img').css({
            width: data.ODIN_BUYING_BAR + '%'
        })
        $('#progress-percent-light').css({
            marginLeft: data.ODIN_BUYING_BAR + '%'
        })
    }

    renderTips()
}

function renderTips() {
    var obj = data.cannGetAmount[_index]
    var key = Object.keys(obj)
    var value = obj[key]
    $('#tips-key').html(key)
    $('#tips-value').html(value)
}
$("#buyButton").click(function() {
    var obj = data.cannGetAmount[_index]
    var key = Object.keys(obj)
    var value = obj[key]
    window.android.buyAction(value, key[0]);
});

// 倒计时
function timeDownInit(time) {
    var timestamp = new Date().setHours(+time, 0, 0, 0)
    var nowDate = new Date().getTime()
    if(nowDate > timestamp) {
        timestamp += 1000*60*60*24   
    }
    $('#time').html(timeDownCalc(timestamp, nowDate))

    clearInterval(timer)
    timer = setInterval(function() {
        nowDate = new Date().getTime()
        if(timestamp < nowDate) {
            clearInterval(timer)
            pageInit()
        } else {
            $('#time').html(timeDownCalc(timestamp, nowDate))
        }
    }, 1000)
}

function timeDownCalc(timestamp, nowDate) {
    timestamp = timestamp - nowDate
    var h = Math.floor(timestamp / 1000 / 60 / 60)
    timestamp = timestamp - h*1000*60*60
    var m = Math.floor(timestamp / 1000 / 60)
    timestamp = timestamp - m*1000*60
    var s = Math.floor(timestamp / 1000)
    return (h > 9 ? h : '0' + h) + ':' + (m > 9 ? m : '0' + m) + ':' + (s > 9 ? s : '0' + s)
}


$('#back').click(function() {
    history.back()
});

$("#explain").click(function() {
  if (data.docUrl) window.open(data.docUrl);
});

$("#remindInfo").click(function() {
    var time = data.ODIN_BUYING_TIME;
    window.android.saveCalenderEvent(time);
//    window.android.saveCalenderEvent("123");
});


$('[data-amount]').click(function () {
    $('[data-amount]').removeClass('btn-active')
    $(this).addClass('btn-active')
    _index = $(this).attr('data-amount')
    renderTips()
})