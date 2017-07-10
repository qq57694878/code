import echarts from 'echarts'

export const echartsThemeConfig=function(){
	console.debug('echats theme config');

	echarts.registerTheme('walden', {
		"color": [
		"#3fb1e3",
		"#6be6c1",
		"#626c91",
		"#a0a7e6",
		"#c4ebad",
		"#96dee8"
		],
		"backgroundColor": "rgba(252,252,252,0)",
		"textStyle": {},
		"title": {
			"textStyle": {
				"color": "#666666"
			},
		"subtextStyle": {
			"color": "#999999"
		}
		},
		"line": {
			"itemStyle": {
				"normal": {
					"borderWidth": "2"
				}
			},
			"lineStyle": {
				"normal": {
					"width": "3"
				}
			},
			"symbolSize": "8",
			"symbol": "emptyCircle",
			"smooth": false
		},
		"radar": {
			"itemStyle": {
				"normal": {
					"borderWidth": "2"
				}
			},
			"lineStyle": {
				"normal": {
					"width": "3"
				}
			},
			"symbolSize": "8",
			"symbol": "emptyCircle",
			"smooth": false
		},
		"bar": {
			"itemStyle": {
				"normal": {
					"barBorderWidth": 0,
					"barBorderColor": "#ccc"
				},
				"emphasis": {
					"barBorderWidth": 0,
					"barBorderColor": "#ccc"
				}
			}
		},
		"pie": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"scatter": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"boxplot": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"parallel": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"sankey": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"funnel": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"gauge": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"candlestick": {
			"itemStyle": {
				"normal": {
					"color": "#e6a0d2",
					"color0": "transparent",
					"borderColor": "#e6a0d2",
					"borderColor0": "#3fb1e3",
					"borderWidth": "2"
				}
			}
		},
		"graph": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			},
			"lineStyle": {
				"normal": {
					"width": "1",
					"color": "#cccccc"
				}
			},
			"symbolSize": "8",
			"symbol": "emptyCircle",
			"smooth": false,
			"color": [
				"#3fb1e3",
			"#6be6c1",
			"#626c91",
			"#a0a7e6",
			"#c4ebad",
			"#96dee8"
				],
			"label": {
				"normal": {
					"textStyle": {
						"color": "#ffffff"
					}
				}
			}
		},
		"map": {
			"itemStyle": {
				"normal": {
					"areaColor": "#eeeeee",
					"borderColor": "#aaaaaa",
					"borderWidth": 0.5
				},
				"emphasis": {
					"areaColor": "rgba(63,177,227,0.25)",
					"borderColor": "#3fb1e3",
					"borderWidth": 1
				}
			},
			"label": {
				"normal": {
					"textStyle": {
						"color": "#ffffff"
					}
				},
				"emphasis": {
					"textStyle": {
						"color": "rgb(63,177,227)"
					}
				}
			}
		},
		"geo": {
			"itemStyle": {
				"normal": {
					"areaColor": "#eeeeee",
					"borderColor": "#aaaaaa",
					"borderWidth": 0.5
				},
				"emphasis": {
					"areaColor": "rgba(63,177,227,0.25)",
					"borderColor": "#3fb1e3",
					"borderWidth": 1
				}
			},
			"label": {
				"normal": {
					"textStyle": {
						"color": "#ffffff"
					}
				},
				"emphasis": {
					"textStyle": {
						"color": "rgb(63,177,227)"
					}
				}
			}
		},
		"categoryAxis": {
			"axisLine": {
				"show": true,
				"lineStyle": {
					"color": "#cccccc"
				}
			},
			"axisTick": {
				"show": false,
				"lineStyle": {
					"color": "#333"
				}
			},
			"axisLabel": {
				"show": true,
				"textStyle": {
					"color": "#999999"
				}
			},
			"splitLine": {
				"show": true,
				"lineStyle": {
					"color": [
						"#eeeeee"
						]
				}
			},
			"splitArea": {
				"show": false,
				"areaStyle": {
					"color": [
						"rgba(250,250,250,0.05)",
					"rgba(200,200,200,0.02)"
						]
				}
			}
		},
		"valueAxis": {
			"axisLine": {
				"show": true,
				"lineStyle": {
					"color": "#cccccc"
				}
			},
			"axisTick": {
				"show": false,
				"lineStyle": {
					"color": "#333"
				}
			},
			"axisLabel": {
				"show": true,
				"textStyle": {
					"color": "#999999"
				}
			},
			"splitLine": {
				"show": true,
				"lineStyle": {
					"color": [
						"#eeeeee"
						]
				}
			},
			"splitArea": {
				"show": false,
				"areaStyle": {
					"color": [
						"rgba(250,250,250,0.05)",
					"rgba(200,200,200,0.02)"
						]
				}
			}
		},
		"logAxis": {
			"axisLine": {
				"show": true,
				"lineStyle": {
					"color": "#cccccc"
				}
			},
			"axisTick": {
				"show": false,
				"lineStyle": {
					"color": "#333"
				}
			},
			"axisLabel": {
				"show": true,
				"textStyle": {
					"color": "#999999"
				}
			},
			"splitLine": {
				"show": true,
				"lineStyle": {
					"color": [
						"#eeeeee"
						]
				}
			},
			"splitArea": {
				"show": false,
				"areaStyle": {
					"color": [
						"rgba(250,250,250,0.05)",
					"rgba(200,200,200,0.02)"
						]
				}
			}
		},
		"timeAxis": {
			"axisLine": {
				"show": true,
				"lineStyle": {
					"color": "#cccccc"
				}
			},
			"axisTick": {
				"show": false,
				"lineStyle": {
					"color": "#333"
				}
			},
			"axisLabel": {
				"show": true,
				"textStyle": {
					"color": "#999999"
				}
			},
			"splitLine": {
				"show": true,
				"lineStyle": {
					"color": [
						"#eeeeee"
						]
				}
			},
			"splitArea": {
				"show": false,
				"areaStyle": {
					"color": [
						"rgba(250,250,250,0.05)",
					"rgba(200,200,200,0.02)"
						]
				}
			}
		},
		"toolbox": {
			"iconStyle": {
				"normal": {
					"borderColor": "#999999"
				},
				"emphasis": {
					"borderColor": "#666666"
				}
			}
		},
		"legend": {
			"textStyle": {
				"color": "#999999"
			}
		},
		"tooltip": {
			"axisPointer": {
				"lineStyle": {
					"color": "#cccccc",
					"width": 1
				},
				"crossStyle": {
					"color": "#cccccc",
					"width": 1
				}
			}
		},
		"timeline": {
			"lineStyle": {
				"color": "#626c91",
				"width": 1
			},
			"itemStyle": {
				"normal": {
					"color": "#626c91",
					"borderWidth": 1
				},
				"emphasis": {
					"color": "#626c91"
				}
			},
			"controlStyle": {
				"normal": {
					"color": "#626c91",
					"borderColor": "#626c91",
					"borderWidth": 0.5
				},
				"emphasis": {
					"color": "#626c91",
					"borderColor": "#626c91",
					"borderWidth": 0.5
				}
			},
			"checkpointStyle": {
				"color": "#3fb1e3",
				"borderColor": "rgba(63,177,227,0.15)"
			},
			"label": {
				"normal": {
					"textStyle": {
						"color": "#626c91"
					}
				},
				"emphasis": {
					"textStyle": {
						"color": "#626c91"
					}
				}
			}
		},
		"visualMap": {
			"color": [
				"#2a99c9",
			"#afe8ff"
				]
		},
		"dataZoom": {
			"backgroundColor": "rgba(255,255,255,0)",
			"dataBackgroundColor": "rgba(222,222,222,1)",
			"fillerColor": "rgba(114,230,212,0.25)",
			"handleColor": "#cccccc",
			"handleSize": "100%",
			"textStyle": {
				"color": "#999999"
			}
		},
		"markPoint": {
			"label": {
				"normal": {
					"textStyle": {
						"color": "#ffffff"
					}
				},
				"emphasis": {
					"textStyle": {
						"color": "#ffffff"
					}
				}
			}
		}
	});

	echarts.registerTheme('wonderland', {
		"color": [
		"#4ea397",
		"#22c3aa",
		"#7bd9a5",
		"#d0648a",
		"#f58db2",
		"#f2b3c9"
		],
		"backgroundColor": "rgba(255,255,255,0)",
		"textStyle": {},
		"title": {
			"textStyle": {
				"color": "#666666"
			},
		"subtextStyle": {
			"color": "#999999"
		}
		},
		"line": {
			"itemStyle": {
				"normal": {
					"borderWidth": "2"
				}
			},
			"lineStyle": {
				"normal": {
					"width": "3"
				}
			},
			"symbolSize": "8",
			"symbol": "emptyCircle",
			"smooth": false
		},
		"radar": {
			"itemStyle": {
				"normal": {
					"borderWidth": "2"
				}
			},
			"lineStyle": {
				"normal": {
					"width": "3"
				}
			},
			"symbolSize": "8",
			"symbol": "emptyCircle",
			"smooth": false
		},
		"bar": {
			"itemStyle": {
				"normal": {
					"barBorderWidth": 0,
					"barBorderColor": "#ccc"
				},
				"emphasis": {
					"barBorderWidth": 0,
					"barBorderColor": "#ccc"
				}
			}
		},
		"pie": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"scatter": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"boxplot": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"parallel": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"sankey": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"funnel": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"gauge": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"candlestick": {
			"itemStyle": {
				"normal": {
					"color": "#d0648a",
					"color0": "transparent",
					"borderColor": "#d0648a",
					"borderColor0": "#22c3aa",
					"borderWidth": "1"
				}
			}
		},
		"graph": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			},
			"lineStyle": {
				"normal": {
					"width": "1",
					"color": "#cccccc"
				}
			},
			"symbolSize": "8",
			"symbol": "emptyCircle",
			"smooth": false,
			"color": [
				"#4ea397",
			"#22c3aa",
			"#7bd9a5",
			"#d0648a",
			"#f58db2",
			"#f2b3c9"
				],
			"label": {
				"normal": {
					"textStyle": {
						"color": "#ffffff"
					}
				}
			}
		},
		"map": {
			"itemStyle": {
				"normal": {
					"areaColor": "#eeeeee",
					"borderColor": "#999999",
					"borderWidth": 0.5
				},
				"emphasis": {
					"areaColor": "rgba(34,195,170,0.25)",
					"borderColor": "#22c3aa",
					"borderWidth": 1
				}
			},
			"label": {
				"normal": {
					"textStyle": {
						"color": "#28544e"
					}
				},
				"emphasis": {
					"textStyle": {
						"color": "rgb(52,158,142)"
					}
				}
			}
		},
		"geo": {
			"itemStyle": {
				"normal": {
					"areaColor": "#eeeeee",
					"borderColor": "#999999",
					"borderWidth": 0.5
				},
				"emphasis": {
					"areaColor": "rgba(34,195,170,0.25)",
					"borderColor": "#22c3aa",
					"borderWidth": 1
				}
			},
			"label": {
				"normal": {
					"textStyle": {
						"color": "#28544e"
					}
				},
				"emphasis": {
					"textStyle": {
						"color": "rgb(52,158,142)"
					}
				}
			}
		},
		"categoryAxis": {
			"axisLine": {
				"show": true,
				"lineStyle": {
					"color": "#cccccc"
				}
			},
			"axisTick": {
				"show": false,
				"lineStyle": {
					"color": "#333"
				}
			},
			"axisLabel": {
				"show": true,
				"textStyle": {
					"color": "#999999"
				}
			},
			"splitLine": {
				"show": true,
				"lineStyle": {
					"color": [
						"#eeeeee"
						]
				}
			},
			"splitArea": {
				"show": false,
				"areaStyle": {
					"color": [
						"rgba(250,250,250,0.05)",
					"rgba(200,200,200,0.02)"
						]
				}
			}
		},
		"valueAxis": {
			"axisLine": {
				"show": true,
				"lineStyle": {
					"color": "#cccccc"
				}
			},
			"axisTick": {
				"show": false,
				"lineStyle": {
					"color": "#333"
				}
			},
			"axisLabel": {
				"show": true,
				"textStyle": {
					"color": "#999999"
				}
			},
			"splitLine": {
				"show": true,
				"lineStyle": {
					"color": [
						"#eeeeee"
						]
				}
			},
			"splitArea": {
				"show": false,
				"areaStyle": {
					"color": [
						"rgba(250,250,250,0.05)",
					"rgba(200,200,200,0.02)"
						]
				}
			}
		},
		"logAxis": {
			"axisLine": {
				"show": true,
				"lineStyle": {
					"color": "#cccccc"
				}
			},
			"axisTick": {
				"show": false,
				"lineStyle": {
					"color": "#333"
				}
			},
			"axisLabel": {
				"show": true,
				"textStyle": {
					"color": "#999999"
				}
			},
			"splitLine": {
				"show": true,
				"lineStyle": {
					"color": [
						"#eeeeee"
						]
				}
			},
			"splitArea": {
				"show": false,
				"areaStyle": {
					"color": [
						"rgba(250,250,250,0.05)",
					"rgba(200,200,200,0.02)"
						]
				}
			}
		},
		"timeAxis": {
			"axisLine": {
				"show": true,
				"lineStyle": {
					"color": "#cccccc"
				}
			},
			"axisTick": {
				"show": false,
				"lineStyle": {
					"color": "#333"
				}
			},
			"axisLabel": {
				"show": true,
				"textStyle": {
					"color": "#999999"
				}
			},
			"splitLine": {
				"show": true,
				"lineStyle": {
					"color": [
						"#eeeeee"
						]
				}
			},
			"splitArea": {
				"show": false,
				"areaStyle": {
					"color": [
						"rgba(250,250,250,0.05)",
					"rgba(200,200,200,0.02)"
						]
				}
			}
		},
		"toolbox": {
			"iconStyle": {
				"normal": {
					"borderColor": "#999999"
				},
				"emphasis": {
					"borderColor": "#666666"
				}
			}
		},
		"legend": {
			"textStyle": {
				"color": "#999999"
			}
		},
		"tooltip": {
			"axisPointer": {
				"lineStyle": {
					"color": "#cccccc",
					"width": 1
				},
				"crossStyle": {
					"color": "#cccccc",
					"width": 1
				}
			}
		},
		"timeline": {
			"lineStyle": {
				"color": "#4ea397",
				"width": 1
			},
			"itemStyle": {
				"normal": {
					"color": "#4ea397",
					"borderWidth": 1
				},
				"emphasis": {
					"color": "#4ea397"
				}
			},
			"controlStyle": {
				"normal": {
					"color": "#4ea397",
					"borderColor": "#4ea397",
					"borderWidth": 0.5
				},
				"emphasis": {
					"color": "#4ea397",
					"borderColor": "#4ea397",
					"borderWidth": 0.5
				}
			},
			"checkpointStyle": {
				"color": "#4ea397",
				"borderColor": "rgba(60,235,210,0.3)"
			},
			"label": {
				"normal": {
					"textStyle": {
						"color": "#4ea397"
					}
				},
				"emphasis": {
					"textStyle": {
						"color": "#4ea397"
					}
				}
			}
		},
		"visualMap": {
			"color": [
				"#d0648a",
			"#22c3aa",
			"#adfff1"
				]
		},
		"dataZoom": {
			"backgroundColor": "rgba(255,255,255,0)",
			"dataBackgroundColor": "rgba(222,222,222,1)",
			"fillerColor": "rgba(114,230,212,0.25)",
			"handleColor": "#cccccc",
			"handleSize": "100%",
			"textStyle": {
				"color": "#999999"
			}
		},
		"markPoint": {
			"label": {
				"normal": {
					"textStyle": {
						"color": "#ffffff"
					}
				},
				"emphasis": {
					"textStyle": {
						"color": "#ffffff"
					}
				}
			}
		}
	});

	echarts.registerTheme('essos', {
		"color": [
		"#893448",
		"#d95850",
		"#eb8146",
		"#ffb248",
		"#f2d643",
		"#ebdba4"
		],
		"backgroundColor": "rgba(242,234,191,0.15)",
		"textStyle": {},
		"title": {
			"textStyle": {
				"color": "#893448"
			},
		"subtextStyle": {
			"color": "#d95850"
		}
		},
		"line": {
			"itemStyle": {
				"normal": {
					"borderWidth": "2"
				}
			},
			"lineStyle": {
				"normal": {
					"width": "2"
				}
			},
			"symbolSize": "6",
			"symbol": "emptyCircle",
			"smooth": true
		},
		"radar": {
			"itemStyle": {
				"normal": {
					"borderWidth": "2"
				}
			},
			"lineStyle": {
				"normal": {
					"width": "2"
				}
			},
			"symbolSize": "6",
			"symbol": "emptyCircle",
			"smooth": true
		},
		"bar": {
			"itemStyle": {
				"normal": {
					"barBorderWidth": 0,
					"barBorderColor": "#ccc"
				},
				"emphasis": {
					"barBorderWidth": 0,
					"barBorderColor": "#ccc"
				}
			}
		},
		"pie": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"scatter": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"boxplot": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"parallel": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"sankey": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"funnel": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"gauge": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				},
				"emphasis": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			}
		},
		"candlestick": {
			"itemStyle": {
				"normal": {
					"color": "#eb8146",
					"color0": "transparent",
					"borderColor": "#d95850",
					"borderColor0": "#58c470",
					"borderWidth": "2"
				}
			}
		},
		"graph": {
			"itemStyle": {
				"normal": {
					"borderWidth": 0,
					"borderColor": "#ccc"
				}
			},
			"lineStyle": {
				"normal": {
					"width": 1,
					"color": "#aaaaaa"
				}
			},
			"symbolSize": "6",
			"symbol": "emptyCircle",
			"smooth": true,
			"color": [
				"#893448",
			"#d95850",
			"#eb8146",
			"#ffb248",
			"#f2d643",
			"#ebdba4"
				],
			"label": {
				"normal": {
					"textStyle": {
						"color": "#ffffff"
					}
				}
			}
		},
		"map": {
			"itemStyle": {
				"normal": {
					"areaColor": "#f3f3f3",
					"borderColor": "#999999",
					"borderWidth": 0.5
				},
				"emphasis": {
					"areaColor": "rgba(255,178,72,1)",
					"borderColor": "#eb8146",
					"borderWidth": 1
				}
			},
			"label": {
				"normal": {
					"textStyle": {
						"color": "#893448"
					}
				},
				"emphasis": {
					"textStyle": {
						"color": "rgb(137,52,72)"
					}
				}
			}
		},
		"geo": {
			"itemStyle": {
				"normal": {
					"areaColor": "#f3f3f3",
					"borderColor": "#999999",
					"borderWidth": 0.5
				},
				"emphasis": {
					"areaColor": "rgba(255,178,72,1)",
					"borderColor": "#eb8146",
					"borderWidth": 1
				}
			},
			"label": {
				"normal": {
					"textStyle": {
						"color": "#893448"
					}
				},
				"emphasis": {
					"textStyle": {
						"color": "rgb(137,52,72)"
					}
				}
			}
		},
		"categoryAxis": {
			"axisLine": {
				"show": true,
				"lineStyle": {
					"color": "#aaaaaa"
				}
			},
			"axisTick": {
				"show": false,
				"lineStyle": {
					"color": "#333"
				}
			},
			"axisLabel": {
				"show": true,
				"textStyle": {
					"color": "#999999"
				}
			},
			"splitLine": {
				"show": true,
				"lineStyle": {
					"color": [
						"#e6e6e6"
						]
				}
			},
			"splitArea": {
				"show": false,
				"areaStyle": {
					"color": [
						"rgba(250,250,250,0.05)",
					"rgba(200,200,200,0.02)"
						]
				}
			}
		},
		"valueAxis": {
			"axisLine": {
				"show": true,
				"lineStyle": {
					"color": "#aaaaaa"
				}
			},
			"axisTick": {
				"show": false,
				"lineStyle": {
					"color": "#333"
				}
			},
			"axisLabel": {
				"show": true,
				"textStyle": {
					"color": "#999999"
				}
			},
			"splitLine": {
				"show": true,
				"lineStyle": {
					"color": [
						"#e6e6e6"
						]
				}
			},
			"splitArea": {
				"show": false,
				"areaStyle": {
					"color": [
						"rgba(250,250,250,0.05)",
					"rgba(200,200,200,0.02)"
						]
				}
			}
		},
		"logAxis": {
			"axisLine": {
				"show": true,
				"lineStyle": {
					"color": "#aaaaaa"
				}
			},
			"axisTick": {
				"show": false,
				"lineStyle": {
					"color": "#333"
				}
			},
			"axisLabel": {
				"show": true,
				"textStyle": {
					"color": "#999999"
				}
			},
			"splitLine": {
				"show": true,
				"lineStyle": {
					"color": [
						"#e6e6e6"
						]
				}
			},
			"splitArea": {
				"show": false,
				"areaStyle": {
					"color": [
						"rgba(250,250,250,0.05)",
					"rgba(200,200,200,0.02)"
						]
				}
			}
		},
		"timeAxis": {
			"axisLine": {
				"show": true,
				"lineStyle": {
					"color": "#aaaaaa"
				}
			},
			"axisTick": {
				"show": false,
				"lineStyle": {
					"color": "#333"
				}
			},
			"axisLabel": {
				"show": true,
				"textStyle": {
					"color": "#999999"
				}
			},
			"splitLine": {
				"show": true,
				"lineStyle": {
					"color": [
						"#e6e6e6"
						]
				}
			},
			"splitArea": {
				"show": false,
				"areaStyle": {
					"color": [
						"rgba(250,250,250,0.05)",
					"rgba(200,200,200,0.02)"
						]
				}
			}
		},
		"toolbox": {
			"iconStyle": {
				"normal": {
					"borderColor": "#999999"
				},
				"emphasis": {
					"borderColor": "#666666"
				}
			}
		},
		"legend": {
			"textStyle": {
				"color": "#999999"
			}
		},
		"tooltip": {
			"axisPointer": {
				"lineStyle": {
					"color": "#cccccc",
					"width": 1
				},
				"crossStyle": {
					"color": "#cccccc",
					"width": 1
				}
			}
		},
		"timeline": {
			"lineStyle": {
				"color": "#893448",
				"width": 1
			},
			"itemStyle": {
				"normal": {
					"color": "#893448",
					"borderWidth": 1
				},
				"emphasis": {
					"color": "#ffb248"
				}
			},
			"controlStyle": {
				"normal": {
					"color": "#893448",
					"borderColor": "#893448",
					"borderWidth": 0.5
				},
				"emphasis": {
					"color": "#893448",
					"borderColor": "#893448",
					"borderWidth": 0.5
				}
			},
			"checkpointStyle": {
				"color": "#eb8146",
				"borderColor": "rgba(255,178,72,0.41)"
			},
			"label": {
				"normal": {
					"textStyle": {
						"color": "#893448"
					}
				},
				"emphasis": {
					"textStyle": {
						"color": "#893448"
					}
				}
			}
		},
		"visualMap": {
			"color": [
				"#893448",
			"#d95850",
			"#eb8146",
			"#ffb248",
			"#f2d643",
			"rgb(247,238,173)"
				]
		},
		"dataZoom": {
			"backgroundColor": "rgba(255,255,255,0)",
			"dataBackgroundColor": "rgba(255,178,72,0.5)",
			"fillerColor": "rgba(255,178,72,0.15)",
			"handleColor": "#ffb248",
			"handleSize": "100%",
			"textStyle": {
				"color": "#333333"
			}
		},
		"markPoint": {
			"label": {
				"normal": {
					"textStyle": {
						"color": "#ffffff"
					}
				},
				"emphasis": {
					"textStyle": {
						"color": "#ffffff"
					}
				}
			}
		}
	});

}
