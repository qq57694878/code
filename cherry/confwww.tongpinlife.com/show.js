/**
 * Created by fangya on 2017/2/16.
 */

var colors = ['#2f7ed8', '#8bbc21', '#0d233a', '#910000', '#1aadce',
    '#492970', '#f28f43', '#77a1e5', '#c42525', '#a6c96a'];
var WS = 'ws://www.tongpinlife.com:9999/api/async-websocket/';
var HTTP = 'http://www.tongpinlife.com:9999/api/monitoringv2/';
$(function () {

    //适配设备全屏
    BoxControl();

    //数据填充
    DataJoin();

    //机房情况
    var room = new WebSocket(WS + 'subscribe_env_items');
    room.onmessage = function (d) {
        window.room = JSON.parse(d.data).datas;
    };

    //服务器情况
    var severs = new WebSocket(WS + 'subscribe_host_items');
    severs.onmessage = function (d) {
        window.servers = JSON.parse(d.data).datas;
    };

    //漏水监控
    var water = new WebSocket(WS + 'subscribe_water_items');
    water.onmessage = function (d) {
        var data = JSON.parse(d.data).datas;
        var html = [];
        for (var i = 0; i < data.length; i++) {
            var item = data[i];
            if (item.signal == 1) {
                html.push('<div class="info green">');
            } else {
                html.push('<div class="info red">');
            }
            html.push('<p><span class="glyphicon glyphicon-tint"></span>' + item.name + '</p>');
            if (item.state == 1) {
                html.push('<p>正常</p>');
            } else {
                html.push('<p>异常</p>');
            }
            html.push('</div>');
        }
        $('#exroom .right').html(html.join(''));
        var $infoobj = $('.conbox.roominfo > .info');
        $infoobj.find('p').css('line-height', $infoobj.height() * 0.5 + 'px');
    };

    //服务器情况
    var severs = new WebSocket(WS + 'subscribe_host_items');
    severs.onmessage = function (d) {
        window.servers = JSON.parse(d.data).datas;
    };

    //网络情况
    var web = new WebSocket(WS + 'subscribe_switch_items');
    web.onmessage = function (d) {
        window.web = JSON.parse(d.data).datas;
    };

    //虚拟机情况
    var vm = new WebSocket(WS + 'subscribe_vm_items');
    vm.onmessage = function (d) {
        var fictitious = JSON.parse(d.data).datas;
        var fhtml = [];
        for (var i = 0; i < fictitious.length; i++) {
            var item = fictitious[i];
            fhtml.push('<div class="tab">');
            fhtml.push('<h1>' + item.platform_name + '</h1>');
            fhtml.push('<div class="items">');
            for (var f = 0; f < item.hosts.length; f++) {
                var fitem = item.hosts[f];
                switch (fitem.signal) {
                    case '0':
                        fhtml.push('<div class="item tip red">');
                        break;
                    case '1':
                        fhtml.push('<div class="item tip green">');
                        break;
                    case '2':
                        fhtml.push('<div class="item tip yellow">');
                        break;
                    default:
                        fhtml.push('<div class="item tip grey">');
                }
                fhtml.push('<p class="names">' + fitem.name + '</p>');
                fhtml.push('<span>名称: ' + fitem.name + '<br>CPU: ' +
                    fitem.cpu + '%<br>上行: ' + fitem.upload_flow +
                    'Kbps<br>下载: ' + fitem.download_flow + 'Kbps</span>');
                fhtml.push('</div>');
            }
            fhtml.push('</div>');
            fhtml.push('</div>');
            fhtml.push('<div class="clearfix"></div>');
        }
        $('.fictitious').html(fhtml.join(''));
    };
});

/**
 * costtime    请求时长
 * datalength  初始绘点数
 * */
var costtime = 5000, datalength = 80;

/**
 * 适配设备全屏
 * */
var BoxControl = function () {
    var wh = $(window).height();
    $('.page').css('height', wh + 'px');
};

/**
 * 数据填充
 * */
var DataJoin = function () {
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    //总体情况
    All();

    //机房情况
    $.getJSON(HTTP + 'init_env_items.do', {}, function (d) {
        if (d.data.datas.length > 0) {
            Room(d.data.datas);
        } else {
            alert('暂无机房数据')
        }
    });

    //网络情况
    Web();

    //服务器情况
    $.getJSON(HTTP + 'init_host_items.do', {}, function (d) {
        if (d.data.datas.length > 0) {
            Sever(d.data.datas);
        } else {
            alert('暂无服务器数据')
        }
    });
};

/**
 * 总体情况
 * */
var All = function () {
    var all = {point: 95, info: {severnum: 8, webnum: 6, ficnum: 43, os: 1}};
    //圆环进度
    Progress(all.point);
    //机房
    $('#oinfo').html(all.info.os);
    //服务器
    $('#sinfo').html(all.info.severnum);
    //网络设备
    $('#winfo').html(all.info.webnum);
    //虚拟机
    $('#finfo').html(all.info.ficnum);

    var lh = $('.all .rightbox .items .item').height();
    $('.all .rightbox .items .item:first-child p').css('line-height', lh / 2 + 'px');
    $('.all .rightbox .items .item').not(':first-child').find('p').css('line-height', lh / 3 + 'px');
};

/**
 * 网络情况
 */
var Web = function () {
    var option = {
        chart: {
            type: 'column'
        },
        title: {
            text: ''
        },
        xAxis: {
            title: '交换机',
            type: 'category',
            categories: []
        },
        yAxis: {
            title: {
                text: '流量(Kbps)'
            }
        },
        tooltip: {},
        plotOptions: {
            column: {
                depth: 25,
                animation: {
                    duration: 1000
                }
            }
        },
        credits: {
            enabled: false
        },
        series: [{
            name: '交换机监控',
            data: []
        }]
    };
    var webchart = new Highcharts.Chart('webcont', option);
    setInterval(function () {
        var web = window.web;
        if (web) {
            webchart.update({
                xAxis: {
                    categories: (function () {
                        var arr = [];
                        for (var i = 0; i < web.length; i++) {
                            arr.push(web[i].name);
                        }
                        return arr;
                    }())
                },
                tooltip: {
                    formatter: function () {
                        var l = (parseFloat(this.y)/1000).toFixed(2);
                        return '<b>' + this.x + '</b><br/>' +
                            '流量：' + l + 'Kbps';
                    }
                },
                series: [{
                    data: (function () {
                        var datas = [];
                        for (var i = 0; i < web.length; i++) {
                            datas.push(parseFloat(web[i].download_flow)*1000);
                        }
                        return datas;
                    }())
                }]
            })
            ;
        }
    }, costtime);
};


/**
 * 机房情况
 **/
var Room = function (room) {
    var exinof = $('#exroom .left');
    //温度
    $('#roomcont1').highcharts({
        chart: {
            type: 'spline',
            events: {
                load: function () {
                    var chart = this;
                    setInterval(function () {
                        if (window.room) {
                            for (var r = 0; r < window.room.length; r++) {
                                var item = window.room[r];
                                var x = (new Date()).getTime(),
                                    y = parseFloat(item.tempreature);
                                chart.series[r].addPoint([x, y], true, true);
                            }
                        }
                    }, costtime);
                }
            }
        },
        title: {
            text: '温度'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150,
            gridLineWidth: 1
        },
        yAxis: {
            title: {
                text: null
            },
            labels: {
                format: '{value}°C'
            }
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                    Highcharts.numberFormat(this.y, 2);
            }
        },
        legend: {
            enabled: false
        },
        credits: {
            enabled: false
        },
        series: (function () {
            var datas = [];
            var html = [];
            for (var r = 0; r < room.length; r++) {
                var item = room[r];
                html.push('<div class="info"><span style="background:' + colors[r]
                    + '"></span><span class="font" style="color:' + colors[r] +
                    '">' + item.name + '</span>' +
                    '<div class="clearfix"></div></div>');
                datas.push({
                    name: item.name,
                    color: colors[r],
                    data: (function () {
                        var data = [],
                            time = (new Date()).getTime();
                        for (var i = -datalength; i <= 0; i += 1) {
                            data.push({
                                x: time + i * costtime,
                                y: 0
                            });
                        }
                        return data;
                    }())
                })
            }
            exinof.html(html.join(''))
            return datas;
        }())
    });

    //湿度
    $('#roomcont2').highcharts({
        chart: {
            type: 'spline',
            events: {
                load: function () {
                    var chart = this;
                    setInterval(function () {
                        if (window.room) {
                            for (var r = 0; r < window.room.length; r++) {
                                var item = window.room[r];
                                var x = (new Date()).getTime(),
                                    y = parseFloat(item.humidity);
                                chart.series[r].addPoint([x, y], true, true);
                            }
                        }
                    }, costtime);
                }
            }
        },
        title: {
            text: '湿度'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150,
            gridLineWidth: 1
        },
        yAxis: {
            title: {
                text: null
            },
            labels: {
                format: '{value}%'
            }
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                    Highcharts.numberFormat(this.y, 2);
            }
        },
        legend: {
            enabled: false
        },
        credits: {
            enabled: false
        },
        series: (function () {
            var datas = [];
            for (var r = 0; r < room.length; r++) {
                var item = room[r];
                datas.push({
                    name: item.name,
                    color: colors[r],
                    data: (function () {
                        var data = [],
                            time = (new Date()).getTime();
                        for (var i = -datalength; i <= 0; i += 1) {
                            data.push({
                                x: time + i * costtime,
                                y: 0
                            });
                        }
                        return data;
                    }())
                })
            }
            return datas;
        }())
    });
};

/**
 * 服务器情况
 * */
var Sever = function (servers) {
    var exinof = $('#exserver');
    //CPU
    $('#severcont1').highcharts({
        chart: {
            type: 'spline',
            marginTop: 50,
            zoomType: 'xy',
            events: {
                load: function () {
                    var chart = this;
                    setInterval(function () {
                        if (window.servers) {
                            var exhtml = [];
                            for (var r = 0; r < window.servers.length; r++) {
                                var item = window.servers[r];
                                var x = (new Date()).getTime(),
                                    y = parseFloat(item.cpu);
                                chart.series[r].addPoint([x, y], true, true);
                                exhtml.push('<div title="'+chart.series[r].name+'" class="info"><span style="background:' + chart.series[r].color
                                    + '"></span><span class="font" style="color:' + chart.series[r].color +
                                    '">' + chart.series[r].name + '</span>' +
                                    '<div class="clearfix"></div></div>');
                            }
                            exinof.html(exhtml.join(''))
                        }
                    }, costtime);
                }
            }
        },
        title: {
            text: 'CPU'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150,
            gridLineWidth: 1
        },
        yAxis: {
            min: 0,
            // max:100,
            minorTickInterval: 'auto',
            title: {
                text: null
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }],
            labels: {
                format: '{value}%'
            }
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                    this.y + '%';
            }
        },
        legend: {
            enabled: false
        },
        credits: {
            enabled: false
        },
        series: (function () {
            var datas = [];
            for (var r = 0; r < servers.length; r++) {
                var item = servers[r];
                datas.push({
                    name: item.name,
                    color: colors[r],
                    data: (function () {
                        var data = [],
                            time = (new Date()).getTime();
                        // for (var i = -item.history_data.length; i < 0; i += 1) {
                        for (var i = -datalength; i < 0; i += 1) {
                            data.push({
                                x: time + i * costtime,
                                // y: parseFloat(item.history_data[i + item.history_data.length].cpu)
                                y: 0
                            });
                        }
                        return data;
                    }())
                })
            }
            return datas;
        }())
    });

    //内存
    $('#severcont2').highcharts({
        chart: {
            type: 'spline',
            marginTop: 50,
            zoomType: 'xy',
            events: {
                load: function () {
                    var chart = this;
                    setInterval(function () {
                        if (window.servers) {
                            for (var r = 0; r < window.servers.length; r++) {
                                var item = window.servers[r];
                                var x = (new Date()).getTime(),
                                    y = parseFloat(item.memory_used);
                                chart.series[r].addPoint([x, y], true, true);
                            }
                        }
                    }, costtime);
                }
            }
        },
        title: {
            text: '内存'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150,
            gridLineWidth: 1
        },
        yAxis: {
            minorTickInterval: 'auto',
            title: {
                text: null
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }],
            labels: {
                format: '{value}G'
            }
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                    this.y + 'G';
            }
        },
        legend: {
            enabled: false
        },
        credits: {
            enabled: false
        },
        series: (function () {
            var datas = [];
            for (var r = 0; r < servers.length; r++) {
                var item = servers[r];
                datas.push({
                    name: item.name,
                    color: colors[r],
                    data: (function () {
                        var data = [],
                            time = (new Date()).getTime();
                        // for (var i = -item.history_data.length; i < 0; i += 1) {
                        for (var i = -datalength; i < 0; i += 1) {
                            data.push({
                                x: time + i * costtime,
                                // y: parseFloat(item.history_data[i + item.history_data.length].memory_used)
                                y: 0
                            });
                        }
                        return data;
                    }())
                })
            }
            return datas;
        }())
    });

    //上行流量
    $('#severcont3').highcharts({
        chart: {
            type: 'spline',
            marginTop: 50,
            zoomType: 'xy',
            events: {
                load: function () {
                    var chart = this;
                    setInterval(function () {
                        if (window.servers) {
                            for (var r = 0; r < window.servers.length; r++) {
                                var item = window.servers[r];
                                var x = (new Date()).getTime(),
                                    y = parseFloat(item.upload_flow);
                                chart.series[r].addPoint([x, y], true, true);
                            }
                        }
                    }, costtime);
                }
            }
        },
        title: {
            text: '上行流量'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150,
            gridLineWidth: 1
        },
        yAxis: {
            title: {
                text: null
            },
            minorTickInterval: 'auto',
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }],
            labels: {
                format: '{value}Kbps'
            }
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                    this.y + 'Kbps';
            }
        },
        legend: {
            enabled: false
        },
        credits: {
            enabled: false
        },
        series: (function () {
            var datas = [];
            for (var r = 0; r < servers.length; r++) {
                var item = servers[r];
                datas.push({
                    name: item.name,
                    color: colors[r],
                    data: (function () {
                        var data = [],
                            time = (new Date()).getTime();
                        // for (var i = -item.history_data.length; i < 0; i += 1) {
                        for (var i = -datalength; i < 0; i += 1) {
                            data.push({
                                x: time + i * costtime,
                                // y: parseFloat(item.history_data[i + item.history_data.length].upload_flow)
                                y: 0
                            });
                        }
                        return data;
                    }())
                })
            }
            return datas;
        }())
    });

    //下载流量
    $('#severcont4').highcharts({
        chart: {
            type: 'spline',
            marginTop: 50,
            zoomType: 'xy',
            events: {
                load: function () {
                    var chart = this;
                    setInterval(function () {
                        if (window.servers) {
                            for (var r = 0; r < window.servers.length; r++) {
                                var item = window.servers[r];
                                var x = (new Date()).getTime(),
                                    y = parseFloat(item.download_flow);
                                chart.series[r].addPoint([x, y], true, true)
                            }
                        }
                    }, costtime);
                }
            }
        },
        title: {
            text: '下载流量'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150,
            gridLineWidth: 1
        },
        yAxis: {
            title: {
                text: null
            },
            minorTickInterval: 'auto',
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }],
            labels: {
                format: '{value}Kbps'
            }
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                    this.y + 'Kbps';
            }
        },
        legend: {
            enabled: false
        },
        credits: {
            enabled: false
        },
        series: (function () {
            var datas = [];
            for (var r = 0; r < servers.length; r++) {
                var item = servers[r];
                datas.push({
                    name: item.name,
                    color: colors[r],
                    data: (function () {
                        var data = [],
                            time = (new Date()).getTime();
                        // for (var i = -item.history_data.length; i < 0; i += 1) {
                        for (var i = -datalength; i < 0; i += 1) {
                            data.push({
                                x: time + i * costtime,
                                // y: parseFloat(item.history_data[i + item.history_data.length].download_flow)
                                y: 0
                            });
                        }
                        return data;
                    }())
                })
            }
            return datas;
        }())
    });
};