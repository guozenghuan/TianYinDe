webpackJsonp([1], {
    "+skl": function (t, e) {}, "0byr": function (t, e) {}, "0cao": function (t, e) {}, NHnr: function (t, e, i) {
        "use strict";
        Object.defineProperty(e, "__esModule", {
            value: !0
        });
        var s = {};
        i.d(s, "initUserData", function () {
            return W
        }), i.d(s, "initOriginData", function () {
            return T
        });
        var n = {};
        i.d(n, "sceneInfo", function () {
            return z
        }), i.d(n, "music", function () {
            return M
        }), i.d(n, "sceneInfoSub", function () {
            return D
        }), i.d(n, "userInfo", function () {
            return $
        }), i.d(n, "additional", function () {
            return R
        });
        var a = i("7+uW"),
            r = i("NYxO"),
            o = i("Dd8w"),
            l = i.n(o),
            c = function (t, e) {
                if (-1 == window.location.href.indexOf("www.zhangu365.com"))
                    if (-1 == window.location.href.indexOf("www.ezhangu.com")) var i = "https://ezhangu.com/editor/v1/";
                    else i = "https://www.ezhangu.com/editor/v1/";
                else i = "https://www.zhangu365.com/editor/v1/";
                return i + "index/showsingle?type=" + t + "&code=" + e
            },
            d = function () {
                if (-1 == window.location.href.indexOf("www.zhangu365.com"))
                    if (-1 == window.location.href.indexOf("www.ezhangu.com")) var t = "https://ezhangu.com/editor/v1/";
                    else t = "https://www.ezhangu.com/editor/v1/";
                else t = "https://www.zhangu365.com/editor/v1/";
                return t + "index/praise"
            },
            u = function () {
                if (-1 == window.location.href.indexOf("www.zhangu365.com"))
                    if (-1 == window.location.href.indexOf("www.ezhangu.com")) var t = "https://ezhangu.com/editor/v1/";
                    else t = "https://www.ezhangu.com/editor/v1/";
                else t = "https://www.zhangu365.com/editor/v1/";
                return t + "index/praisecount"
            },
            h = function () {
                if (-1 == window.location.href.indexOf("www.zhangu365.com"))
                    if (-1 == window.location.href.indexOf("www.ezhangu.com")) var t = "https://ezhangu.com/editor/v1/";
                    else t = "https://www.ezhangu.com/editor/v1/";
                else t = "https://www.zhangu365.com/editor/v1/";
                return t + "index/submitform"
            },
            p = function () {
                if (-1 == window.location.href.indexOf("www.zhangu365.com"))
                    if (-1 == window.location.href.indexOf("www.ezhangu.com")) var t = "https://ezhangu.com/editor/v1/";
                    else t = "https://www.ezhangu.com/editor/v1/";
                else t = "https://www.zhangu365.com/editor/v1/";
                return t + "index/resimg"
            },
            f = i("7t+N"),
            g = i.n(f),
            m = {
                data: function () {
                    return {
                        endShow: !1,
                        tipShow: !0,
                        isEnd: !1,
                        msTime: {
                            show: !1,
                            day: 0,
                            hour: 0,
                            minutes: 0,
                            seconds: 0
                        },
                        star: "",
                        end: "",
                        current: ""
                    }
                }, props: {
                    item: {
                        type: Object,
                        default: null
                    },
                    currentTime: {
                        type: Number
                    },
                    startTime: {
                        type: Number
                    },
                    endTime: {
                        type: Number
                    },
                    endText: {
                        type: String,
                        default: "已结束"
                    },
                    scale: {
                        type: Number
                    }
                }, watch: {
                    endTime: function (t) {
                        this.init()
                    }
                }, mounted: function () {
                    this.init()
                }, methods: {
                    setLineHeight: function (t) {
                        var e = t.style,
                            i = "",
                            s = this.scale;
                        for (var t in e) {
                            var n = e[t];
                            "height" === t ? i += "lineHeight:" + (n - 20) * s * .5 + "px;" : "color" === t && (i += t + ":" + n + ";")
                        }
                        return i
                    }, setStyle: function (t) {
                        var e = t.style,
                            i = "";
                        for (var t in e) {
                            var s = e[t];
                            if ("width" === t) i += t + ":" + (s * this.scale - 40) / 4 + "px;";
                            else if ("backgroundColor" === t) {
                                if ("" == t) {
                                    i += t + ":rgb(0,0,0)";
                                    continue
                                }
                                i += t + ":" + s + ";"
                            }
                        }
                        return i
                    }, init: function () {
                        var t = this;
                        10 == this.startTime.toString().length ? this.star = 1e3 * this.startTime : this.star = this.startTime, 10 == this.endTime.toString().length ? this.end = 1e3 * this.endTime : this.end = this.endTime, this.currentTime ? 10 == this.currentTime.toString().length ? this.current = 1e3 * this.currentTime : this.current = this.currentTime : this.current = (new Date).getTime(), this.end < this.current ? this.$set(this, "endShow", !0) : this.current < this.star ? (this.$set(this, "tipShow", !0), setInterval(function () {
                            t.runTime(t.star, t.current, t.start_message, !1)
                        }, 1e3)) : (this.end > this.current && this.star < this.current || this.star == this.current) && (this.$set(this, "tipShow", !1), this.msTime.show = !0, setInterval(function () {
                            t.runTime(t.end, (new Date).getTime(), t.end_message, !0)
                        }, 1e3))
                    }, setInStyle: function (t) {
                        var e = .01 * this.scale,
                            i = t.style,
                            s = "",
                            n = !1;
                        for (var t in i) {
                            var a = i[t];
                            if ("width" === t) {
                                if (0 == isNaN(a)) {
                                    s += t + ":" + a * e + "px;";
                                    continue
                                }
                                s += t + ":" + a * e + ";"
                            } else if ("height" === t) {
                                if (0 == isNaN(a)) {
                                    s += t + ":" + a * e + "px;";
                                    continue
                                }
                                s += t + ":" + a * e + "px;"
                            } else if ("top" === t) {
                                if (0 == isNaN(a)) {
                                    s += t + ":" + a + "px;";
                                    continue
                                }
                                s += t + ":" + a + ";"
                            } else if ("left" === t) {
                                if (0 == isNaN(a)) {
                                    s += t + ":" + a + "px;";
                                    continue
                                }
                                s += t + ":" + a + ";"
                            } else if ("backgroundColor" === t) {
                                if ("" == t) {
                                    s += t + ":rgb(0,0,0)";
                                    continue
                                }
                                s += t + ":" + a + ";"
                            } else if ("borderRadius" === t) s += t + ":" + (a = "string" == typeof a ? parseInt(a.replace(/[^0-9]/gi, "")) : a) * e + "px;";
                            else if ("word-wrap" == t) n = !0;
                            else if ("fontSize" == t) s += t + ":" + (a = "string" == typeof a ? parseInt(a.replace(/[^0-9]/gi, "")) : a) * e + "px;";
                            else {
                                if ("backgroundImage" == t) continue;
                                if ("backgroundSize" == t) continue;
                                if ("backgroundPosition" == t) continue;
                                if ("borderWidth" == t) continue;
                                if ("borderStyle" == t) continue;
                                if ("borderColor" == t) continue;
                                if ("lineHeight" == t) continue;
                                s += t + ":" + a + ";"
                            }
                        }
                        return n || (s += "word-wrap:break-word"), s
                    }, runTime: function (t, e, i, s) {
                        var n = this.msTime,
                            a = t - e;
                        a > 0 ? (this.msTime.show = !0, n.day = Math.floor(a / 864e5), a -= 864e5 * n.day, n.hour = Math.floor(a / 36e5), a -= 36e5 * n.hour, n.minutes = Math.floor(a / 6e4), a -= 6e4 * n.minutes, n.seconds = Math.floor(a / 1e3).toFixed(0), a -= 1e3 * n.seconds, n.hour < 10 && (n.hour = "0" + n.hour), n.minutes < 10 && (n.minutes = "0" + n.minutes), n.seconds < 10 && (n.seconds = "0" + n.seconds)) : i()
                    }, start_message: function () {
                        var t = this;
                        this.$set(this, "tipShow", !1), this.$emit("start_callback", this.msTime.show), setTimeout(function () {
                            t.runTime(t.end, t.star, t.end_message, !0)
                        }, 1)
                    }, end_message: function () {
                        this.msTime.show = !1, this.$emit("end_callback", this.msTime.show)
                    }
                }
            },
            v = {
                render: function () {
                    var t = this,
                        e = t.$createElement,
                        i = t._self._c || e;
                    return i("div", [t.msTime.show ? i("div", {
                        staticClass: "countdownbox"
                    }, [i("div", {
                        staticClass: "timepart",
                        style: t.setStyle(t.item)
                    }, [i("h1", {
                        style: t.setLineHeight(t.item)
                    }, [t._v(t._s(t.msTime.day))]), t._v(" "), i("p", {
                        style: t.setLineHeight(t.item)
                    }, [t._v("天")])]), t._v(" "), i("div", {
                        staticClass: "timepart",
                        style: t.setStyle(t.item)
                    }, [i("h1", {
                        style: t.setLineHeight(t.item)
                    }, [t._v(t._s(t.msTime.hour))]), t._v(" "), i("p", {
                        style: t.setLineHeight(t.item)
                    }, [t._v("时")])]), t._v(" "), i("div", {
                        staticClass: "timepart",
                        style: t.setStyle(t.item)
                    }, [i("h1", {
                        style: t.setLineHeight(t.item)
                    }, [t._v(t._s(t.msTime.minutes))]), t._v(" "), i("p", {
                        style: t.setLineHeight(t.item)
                    }, [t._v("分")])]), t._v(" "), i("div", {
                        staticClass: "timepart",
                        style: t.setStyle(t.item)
                    }, [i("h1", {
                        style: t.setLineHeight(t.item)
                    }, [t._v(t._s(t.msTime.seconds))]), t._v(" "), i("p", {
                        style: t.setLineHeight(t.item)
                    }, [t._v("秒")])])]) : t._e(), t._v(" "), t.msTime.show ? t._e() : i("div", {
                        staticClass: "countdownwrap"
                    }, [i("span", {
                        staticClass: "text",
                        staticStyle: {
                            display: "block"
                        },
                        style: t.setInStyle(t.item)
                    }, [i("span", {
                        staticClass: "num"
                    }, [t._v(t._s(t.endText))])])])])
                }, staticRenderFns: []
            };
        var y = i("VU/8")(m, v, !1, function (t) {
                i("0cao")
            }, "data-v-21a28f44", null).exports,
            w = {
                name: "main-editor",
                data: function () {
                    return {
                        scaleWidth: 0,
                        scaleHidth: 0,
                        isPlay: !0,
                        height: 0,
                        resImg: 0,
                        i: 0
                    }
                }, computed: l()({}, Object(r.b)(["sceneInfo", "additional", "sceneInfoSub", "music"]), {
                    canvasHeight: function () {
                        if (void 0 !== this.sceneInfo && void 0 !== this.sceneInfo.canvasSize) {
                            var t = this.scaleWidth;
                            return this.sceneInfo.canvasSize.height * t * .5 + "px"
                        }
                    }, canvasWidth: function () {
                        if (void 0 !== this.sceneInfo && void 0 !== this.sceneInfo.canvasSize) {
                            var t = this.scaleWidth;
                            return this.sceneInfo.canvasSize.width * t * .5 + "px"
                        }
                    }, isMusic: function () {
                        return (void 0 == this.$route.query.width || 720 != this.$route.query.width) && ("" != this.sceneInfo.music && (void 0 !== this.sceneInfo.music && "" != this.sceneInfo.music.name))
                    }
                }),
                components: {
                    countdownbox: y
                },
                created: function () {
                    /*var t = this;
                    this.initOriginData(), void 0 == this.$route.query.width ? this.scaleWidth = g()(window).width() / 320 : this.scaleWidth = this.$route.query.width / 320, this.height = g()(window).height(), this.$http.post(p()).then(function (e) {
                        t.resImg = e.data.code
                    }).catch(function (e) {
                        t.$Message.error("网络开小差啦~")
                    })*/
                }, methods: {
                    goLocation: function (t) {
                        var e = this,
                            i = new BMap.Geolocation,
                            s = new BMap.Geocoder;
                        i.getCurrentPosition(function (i) {
                            this.getStatus() == BMAP_STATUS_SUCCESS ? s.getLocation(i.point, function (s) {
                                if (s) {
                                    new BMap.Marker(i.point);
                                    e.lng = i.point.lng, e.lat = i.point.lat;
                                    var n = {};
                                    n.start = {
                                        address: s.surroundingPois[0].address,
                                        msg: s.addressComponents,
                                        lat: i.point.lat,
                                        lng: i.point.lng
                                    }, n.end = {
                                        address: t.address,
                                        lat: t.location[0],
                                        lng: t.location[1]
                                    }, e.navication(n)
                                }
                            }) : alert("failed" + this.getStatus())
                        }, {
                            enableHighAccuracy: !0
                        })
                    }, navication: function (t) {
                        window.parent.location.href = "http://api.map.baidu.com/direction?origin=latlng:" + t.start.lat + "," + t.start.lng + "|name:" + t.start.address + "&destination=" + t.end.address + "&mode=driving&region=" + t.start.msg.province + "&output=html&src=yourCompanyName|yourAppName"
                    }, showimg: function (t, e) {
                        if (void 0 !== t.picId && null != t.picId && "" != t.picId) {
                            var i = t.picId,
                                s = 0;
                            /.(gif)$/.test(i) && (s = 1);
                            var n = 0;
                            return "100%" == e || void 0 === e ? n = 7 : (n = Math.ceil(e / 100)) > 9 && (n = 9), -1 != i.indexOf("http://") && (i = i.replace("http://", "https://")), 1 == s ? n > 6 ? i + "?x-oss-process=image/resize,w_" + n + "00" : i : i + "?x-oss-process=image/resize,w_" + n + "00"
                        }
                    }, initOriginData: function () {
                        /*var t = this;
                        this.$http.post(c(this.$route.query.type, this.$route.query.code)).then(function (e) {
                            1 == e.data.code ? t.$store.dispatch("initOriginData", e.data.data) : t.$Message.error(e.data.msg)
                        }).catch(function (e) {
                            t.$Message.error("网络开小差啦~")
                        })*/
                    }, playOrPauseMusic: function () {
                        var t = document.getElementById("audio2");
                        t.paused ? (t.play(), this.isPlay = !0) : (this.isPlay = !1, t.pause())
                    }, setBgStyle: function () {
                        if (void 0 !== this.sceneInfo && void 0 !== this.sceneInfo.background) return 0 == this.sceneInfo.background.backgroundImage ? "backgroundColor:" + this.sceneInfo.background.backgroundColor + ";backgroundSize:" + this.sceneInfo.background.backgroundSize + "%;backgroundRepeat:" + this.sceneInfo.background.backgroundRepeat + ";" : "backgroundColor:" + this.sceneInfo.background.backgroundColor + ";backgroundSize:" + this.sceneInfo.background.backgroundSize + ";backgroundRepeat:" + this.sceneInfo.background.backgroundRepeat + ";backgroundImage:url(" + this.sceneInfo.background.backgroundImage + ");"
                    }, setFontScale: function () {
                        return ""
                    }, setWrapperStyle: function (t, e) {
                        var i = t.style,
                            s = "";
                        for (var n in i) {
                            var a = i[n];
                            "width" === n && (s += n + ":" + a * this.scaleWidth + "px;"), "height" === n && (s += n + ":" + a * this.scaleWidth + "px;", s += "lineHeight:" + a * this.scaleWidth + "px;"), "top" === n && (s += n + ":" + a * this.scaleWidth + "px;"), "left" === n && (s += n + ":" + a * this.scaleWidth + "px;"), "z-index" === n && (s += n + ":" + a + ";"), "rotate" === n && (s += "transform:rotate(" + a + "deg);"), "linkButton" != t.type && "i" != t.type && "s" != t.type && "r" != t.type && "c" != t.type && "p" != t.type && "countDown" != t.type && "interActionButton" != t.type && "backgroundColor" === n && (s += n + ":" + a + ";"), "color" === n && (s += n + ":" + a + ";"), "fontTag" === n && (s += "fontFamily:" + a + ";"), "fontSize" === n && (s += n + ":" + a * this.scaleWidth + "px;"), "textShadow" == n && (s += n + ":" + a + ";"), "opacity" == n && (s += n + ":" + (1 - a) + ";")
                        }
                        return s + ("z-index:" + 2 * e + ";")
                    }, setWrapperStyle2: function (t, e) {
                        var i = t.style,
                            s = "";
                        for (var n in i) {
                            var a = i[n];
                            "width" === n && (s += n + ":" + a * this.scaleWidth + "px;"), "height" === n && (s += n + ":" + a * this.scaleWidth + "px;", "text" !== t.type && (s += "lineHeight:" + a * this.scaleWidth + "px;")), "top" === n && (s += n + ":" + a * this.scaleWidth + "px;"), "left" === n && (s += n + ":" + a * this.scaleWidth + "px;"), "z-index" === n && (s += n + ":" + a + ";"), "linkButton" != t.type && "i" != t.type && "s" != t.type && "r" != t.type && "c" != t.type && "p" != t.type && "countDown" != t.type && "interActionButton" != t.type && "backgroundColor" === n && (s += n + ":" + a + ";"), "color" === n && (s += n + ":" + a + ";"), "fontTag" === n && (s += "fontFamily:" + a + ";"), "fontSize" === n && (s += n + ":" + a * this.scaleWidth + "px;"), "textShadow" == n && (s += n + ":" + a + ";"), "opacity" == n && (s += n + ":" + (1 - a) + ";")
                        }
                        return s + ("z-index:" + e + ";")
                    }, setRadioStyle: function (t, e) {
                        var i = t.style;
                        if (3 != e) {
                            var s = "";
                            for (var n in i) {
                                var a = i[n];
                                if ("width" === n) {
                                    if (0 == isNaN(a)) {
                                        s += n + ":" + a * this.scaleWidth + "px;";
                                        continue
                                    }
                                    s += n + ":" + a * this.scaleWidth + ";"
                                } else {
                                    if ("height" === n) {
                                        "p" != t.type && "g" != t.type || (s += n + ":" + a * this.scaleWidth + "px;", s += "lineHeight:" + a * this.scaleWidth + "px;");
                                        continue
                                    }
                                    if ("top" === n) {
                                        if (0 == isNaN(a)) {
                                            s += n + ":" + a + "px;";
                                            continue
                                        }
                                        s += n + ":" + a + ";"
                                    } else if ("left" === n) {
                                        if (0 == isNaN(a)) {
                                            s += n + ":" + a + "px;";
                                            continue
                                        }
                                        s += n + ":" + a + ";"
                                    } else if ("backgroundColor" === n) "r" != t.type && "c" != t.type && (s += n + ":" + a + ";");
                                    else {
                                        if ("borderRadius" === n) {
                                            "p" == t.type && (s += n + ":" + a + ";");
                                            continue
                                        }
                                        if ("borderColor" === n) {
                                            "p" == t.type && (s += n + ":" + a + ";");
                                            continue
                                        }
                                        if ("fontSize" == n) s += n + ":" + (a = "string" == typeof a ? parseInt(a.replace(/[^0-9]/gi, "")) : a) * this.scaleWidth + "px;";
                                        else {
                                            if ("borderWidth" == n) {
                                                "p" == t.type && (s += n + ":" + a + ";");
                                                continue
                                            }
                                            if ("lineHeight" == n) "p" != t.type && "g" != t.type && (s += n + ":" + parseInt(a) * this.scaleWidth + "px;");
                                            else {
                                                if (0 == n.indexOf("padding")) continue;
                                                s += "opacity" == n ? n + ":" + (1 - a) + ";" : n + ":" + a + ";"
                                            }
                                        }
                                    }
                                }
                            }
                            return s
                        }
                    }, setRadioTitle: function (t, e) {
                        var i = t.style;
                        if (3 != e) {
                            var s = "";
                            for (var n in i) {
                                var a = i[n];
                                if ("background" === n) s += n + ":" + a + ";";
                                else if ("backgroundColor" == n) s += n + ":" + a + ";";
                                else if ("fontSize" == n) s += n + ":" + (a = "string" == typeof a ? parseInt(a.replace(/[^0-9]/gi, "")) : a) * this.scaleWidth + "px;";
                                else if ("lineHeight" == n) s += n + ":" + parseInt(a) * this.scaleWidth + "px;";
                                else if ("fontTag" == n) s += n + ":" + a + ";";
                                else {
                                    if ("color" != n) continue;
                                    s += n + ":" + a + ";"
                                }
                            }
                            return s
                        }
                    }, setBtnStyle: function (t) {
                        var e = "";
                        for (var i in t) {
                            var s = t[i];
                            "fontSize" === i && (e += i + ":" + (s = "string" == typeof s ? parseInt(s.replace(/[^0-9]/gi, "")) : s) * this.scaleWidth + "px;")
                        }
                        return e
                    }, setTextStyle: function (t) {
                        var e = t.style.width,
                            i = t.style.height,
                            s = t.style.fontTag,
                            n = t.style.fontSize,
                            a = t.style.color,
                            r = t.style.letterSpacing,
                            o = t.style.textDecoration,
                            l = t.style.backgroundColor,
                            c = t.style.borderStyle,
                            d = t.style.borderColor,
                            u = t.style.borderWidth,
                            h = "string" == typeof t.style.borderRadius ? parseInt(t.style.borderRadius.replace(/[^0-9]/gi, "")) : t.style.borderRadius;
                        return "text" == t.type ? "color:" + a + ";width:" + e * this.scaleWidth + "px;height:" + i * this.scaleWidth + "px;fontFamily:" + s + ";fontSize:" + parseInt(n) * this.scaleWidth + "px;textDecoration:" + o + ";backgroundColor:" + l + ";letterSpacing:" + parseInt(r) * this.scaleHeight : "color:" + a + ";width:" + e * this.scaleWidth + "px;height:" + i * this.scaleWidth + "px;fontFamily:" + s + ";fontSize:" + parseInt(n) * this.scaleWidth + "px;textDecoration:" + o + ";backgroundColor:" + l + ";borderWidth:" + u + ";borderStyle:" + c + ";borderColor:" + d + ";borderRadius:" + h + "px;letterSpacing:" + parseInt(r) * this.scaleHeight
                    }, setRenderStyle: function (t) {
                        var e = t.style,
                            i = "";
                        for (var s in e) {
                            var n = e[s];
                            if ("width" === s && (i += s + ":" + n * this.scaleWidth + "px;", "new-form" != t.type && (i += "margin-left:-" + n * this.scaleWidth * .5 + "px;"), "img" == t.type && (i += "background-size:100% 100%;")), "height" === s && (i += s + ":" + n * this.scaleWidth + "px;", "new-form" != t.type ? i += "margin-top:-" + n * this.scaleWidth * .5 + "px;" : i += s + ":auto;", "countDown" == t.type && (i += "line-height:" + n * this.scaleWidth + "px;")), "opacity" === s && (i += s + ":" + (1 - n) + ";"), "line-height" === s && (i += s + ":" + n + ";"), "color" === s && (i += s + ":" + n + ";"), "fontSize" === s && (i += s + ":" + n * this.scaleWidth + "px;"), "textAlign" === s && (i += s + ":" + n + ";"), "backgroundColor" == s) {
                                if ("countDown" == t.type) continue;
                                i += s + ":" + n + ";"
                            }
                            "letterSpacing" === s && (i += s + ":" + n + "px;"), "lineHeight" === s && (i += s + ":" + n + ";"), "fontWeight" === s && (i += s + ":" + n + ";"), "fontStyle" === s && (i += s + ":" + n + ";"), "textDecoration" === s && (i += s + ":" + n + ";"), "writingMode" === s && (i += s + ":" + n + ";"), "borderRadius" === s && (i += s + ":" + parseInt(n * this.scaleWidth) + "px;"), "boxShadow" === s && (i += s + ":black 0px 0px " + n + "px;"), "borderStyle" == s && (i += s + ":" + n + ";"), "borderColor" == s && (i += s + ":" + n + ";"), "borderWidth" == s && (i += s + ":" + n + ";")
                        }
                        if ("img" == t.type) {
                            if (void 0 !== t.picId && "undefined" !== t.picId && null !== t.picId) {
                                var a = t.picId;
                                return -1 != a.indexOf("http://") && (a = a.replace("http://", "https://")), i + ("background-image:url(" + this.showimg(a, e.width) + ");")
                            }
                            return ""
                        }
                        return i
                    }, setSpanStyle: function (t) {
                        var e = "";
                        for (var i in t) {
                            var s = t[i];
                            "height" === i ? e += i + ":" + s * this.scaleWidth + "px;" : "fontSize" === i ? e += i + ":" + s * this.scaleWidth + "px;" : "color" === i ? e += i + ":" + s + ";" : "fontTag" === i && (e += i + ":" + s + ";")
                        }
                        return e
                    }, setInputHeight: function (t, e) {
                        var i = "",
                            s = "textfield" == e ? 50 : 100;
                        return i += "height:" + s * this.scaleWidth + "px;", i += "font-size:" + s * this.scaleWidth * .2 + "px;", "'border-color':" + t.style.formcolor + ";" + i
                    }, setFormTextSize1: function (t) {
                        var e = .01 * this.currentScale,
                            i = void 0 === t.style.fontSize ? 15 : t.style.fontSize,
                            s = "fontSize:" + i * e + "px;height:" + i * e * 2 + "px;line-height: " + i * e * 2 + "px;";
                        return s += "color :" + t.style.color + ";"
                    }, setFormTextSize: function (t) {
                        var e = void 0 === t.style.fontSize ? 15 : t.style.fontSize,
                            i = "fontSize:" + parseInt(e) * this.scaleWidth + "px;height:" + t.style.height * this.scaleWidth + "px;lineHeight: " + t.style.height * this.scaleWidth + "px;fontFamily:" + t.style.fontTag + ";";
                        return i += "color :" + t.style.color + ";"
                    }, setSubmitHeight: function (t) {
                        var e = void 0 === t.style.fontSize ? 15 : t.style.fontSize;
                        return "line-height:" + 60 * this.scaleWidth + "px;height:" + 60 * this.scaleWidth + "px;background-color:" + t.style.formcolor + ";fontSize:" + parseInt(e) * this.scaleWidth + "px;"
                    }, setShapeHtml1: function (t, e) {
                        var i = this;
                        g()(document).ready(function () {
                            var s;
                            if (t.shapeId) {
                                var n = g()(".svg1_" + e);
                                (s = document.createElementNS("http://www.w3.org/2000/svg", "svg")).setAttribute("class", "element svg-element"), g.a.ajax({
                                    type: "GET",
                                    url: t.shapeId,
                                    async: !1,
                                    dataType: "xml",
                                    success: function (a) {
                                        i.jeixiSvg(t, s, a.getElementsByTagName("svg"), e, n)
                                    }
                                })
                            }
                        })
                    }, jeixiSvg: function (t, e, i, s, n) {
                        var a = i,
                            r = t.colorScheme ? t.colorScheme : {};
                        if (t.colorScheme.length) {
                            var o = 0;
                            g()(a).find("[fill], [style*='fill'], [stroke]").each(function (t) {
                                if (void 0 == r[o]) {
                                    var e = o - 1;
                                    "none" != g()(this).attr("fill") && void 0 != g()(this).attr("fill") ? (g()(this).attr("fill", r["color" + e]), o += 1) : "none" != g()(this).attr("stroke") && void 0 != g()(this).attr("stroke") && (g()(this).attr("stroke", r["color" + e]), o += 1)
                                } else "none" != g()(this).attr("fill") && void 0 != g()(this).attr("fill") ? (g()(this).attr("fill", r[o]), o += 1) : "none" != g()(this).attr("stroke") && void 0 != g()(this).attr("stroke") && (g()(this).attr("stroke", r[o]), o += 1)
                            })
                        }
                        g()(a).attr({
                            width: t.style.width * this.scaleWidth,
                            height: t.style.height * this.scaleWidth,
                            preserveAspectRatio: "none meet"
                        }), (e = a[0]).setAttribute("preserveAspectRatio", "none"), e.setAttribute("width", "100%"), e.setAttribute("height", "100%"), e.setAttribute("style", "position:absolute"), e.id = "svg1_" + s, g()(n).empty().append(e)
                    }, setShapeHtml: function (t) {
                        var e = g()(t.svgDom);
                        return t.colorScheme.length ? (g()(e).find("#Page-1").length > 0 ? g()(e).find("#Page-1").find("[fill]").each(function (e) {
                            g()(this).attr("fill", t.colorScheme[e])
                        }) : g()(e).find("[fill]").each(function (e) {
                            g()(this).attr("fill", t.colorScheme[e])
                        }), g()(e).attr({
                            width: t.style.width * this.scaleWidth,
                            height: t.style.height * this.scaleWidth,
                            preserveAspectRatio: "none meet"
                        }), e.prop("outerHTML")) : t.svgDom
                    }, setUrl: function (t) {
                        return "" != t.url && void 0 != t.url ? (-1 == t.url.indexOf("http://") && -1 == t.url.indexOf("https://") && (t.url = "http://" + t.url), "javascript:window.parent.location.href='" + t.url + "'") : "tel://" + t.tel
                    }, requestUrlOld: function (t, e, i, s) {
                        var n = this;
                        this.$http({
                            method: "post",
                            url: e,
                            data: t
                        }).then(function (t) {
                            1 == t.data.code ? (s && (g()(".new-form-element").find("input").val(""), g()(".new-form-element").find("textarea").val("")), n.$Message.success(t.data.msg)) : n.$Message.error(t.data.msg)
                        }).catch(function (t) {
                            n.$Message.error("网络开小差啦~")
                        })
                    }, formBtnClick: function (t) {
                        var e = t,
                            i = e.options,
                            s = {};
                        for (var n in i) {
                            var a = i[n],
                                r = "input" + n,
                                o = g()("#" + r).val();
                            if (o = g.a.trim(o), s[n] = {}, s[n].name = a.name, s[n].val = o, a.requiredFlag) {
                                if (0 == o.length) return this.$Message.error(a.placeholder), !1;
                                if (o.length > 30) return this.$Message.error("字数应小于30个字符"), !1
                            }
                        }
                        var l = {
                            oid: this.sceneInfoSub.id,
                            pageid: this.sceneInfo.id,
                            formid: this.sceneInfo.id,
                            content: s,
                            type: this.additional.type
                        };
                        this.requestUrlOld(l, h(), e, !0)
                    }, requestUrl: function (t, e, i, s) {
                        var n = this;
                        this.$http({
                            method: "post",
                            url: i,
                            data: t
                        }).then(function (e) {
                            1 == e.data.code ? s ? (g()(".inputs").find("input").val(""), g()(".inputs").find("input").nextAll("span").show(), n.$Message.success(e.data.msg)) : (g()("#" + t.zanid).find("span").text(e.data.data.num), n.$Message.success(e.data.msg)) : n.$Message.error(e.data.msg)
                        }).catch(function (t) {
                            n.$Message.error("网络开小差啦~")
                        })
                    }, submitZan: function (t) {
                        var e = new URLSearchParams,
                            i = {
                                oid: this.sceneInfoSub.id,
                                pageid: this.sceneInfoSub.id,
                                zanid: t.id,
                                type: this.additional.type
                            };
                        this.requestUrl(i, e, d(), !1)
                    }, getZanCount: function (t) {
                        var e = this;
                        g()(document).ready(function () {
                            var i = g()("#" + t.id),
                                s = u();
                            0 != i.attr("data-flag") && void 0 !== i.attr("data-flag") || e.$http({
                                method: "post",
                                url: s,
                                data: {
                                    oid: e.sceneInfoSub.id,
                                    pageid: e.sceneInfoSub.id,
                                    zanid: t.id,
                                    type: e.additional.type
                                }
                            }).then(function (t) {
                                i.attr("data-flag", 1), t.data.data.num > 0 && i.find("span").text(t.data.data.num)
                            }).catch(function (t) {})
                        })
                    }, submitData: function (t, e, i) {
                        for (var s, n = new URLSearchParams, a = {}, r = 0; r < e.length; r++)
                            if ("i" == e[r].type) {
                                if (s = g()(i.target).parents(".elementCanvas").find("#" + e[r].id).val(), 1 == e[r].require && ("" == s || "undefined" == s || void 0 == s)) return void this.$Message.error("请补全信息");
                                if ("undefined" !== s) {
                                    var o = e[r].placeholder;
                                    if ("姓名" == o) {
                                        if (s.length > 15) return void this.$Message.error("姓名过长")
                                    } else if ("手机" == o) {
                                        if (!/^1(3|4|5|7|8)\d{9}$/.test(s)) return void this.$Message.error("手机号有误")
                                    } else if ("邮箱" == o) {
                                        if (!/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/.test(s)) return void this.$Message.error("邮箱地址有误")
                                    } else if ("文本" == o && s.length > 150) return void this.$Message.error("文本过长");
                                    a[e[r].id] = s
                                }
                                "s" == e[r].type && (n.ok = e[r].ok)
                            } else if ("r" == e[r].type) {
                                var l = g()('input:radio[name="' + e[r].str + '"]:checked').val();
                                if ("1" === e[r].require && ("" == l || "undefined" == l || void 0 == l)) return void this.$Message.success("请补全信息");
                                a[e[r].id] = l
                            } else if ("c" == e[r].type) {
                                var c = document.getElementsByName(e[r].str),
                                    d = [];
                                for (var u in c) c[u].checked && d.push(c[u].value);
                                if ("1" === e[r].require && 0 == d.length) return void this.$Message.success("请补全信息");
                                a[e[r].id] = d
                            } else if ("p" == e[r].type) {
                                l = g()("#" + e[r].id + " option:selected").text();
                                if ("1" === e[r].require && "undefined" === l) return void this.$Message.success("请补全信息");
                                a[e[r].id] = l
                            } else if ("g" == e[r].type) {
                                l = e[r].value;
                                if ("1" === e[r].require && 0 === l) return void this.$Message.success("请补全信息");
                                a[e[r].id] = l
                            }
                        n.content = a;
                        var p = {
                            oid: this.sceneInfoSub.id,
                            pageid: this.sceneInfo.id,
                            formid: this.sceneInfo.id,
                            content: n.content,
                            type: this.additional.type
                        };
                        this.requestUrl(p, n, h(), !0)
                    }, inputfocus: function (t) {
                        g()(t.target).nextAll("span").hide()
                    }, inputblur: function (t) {
                        "" == g()(t.target).val() ? g()(t.target).nextAll("span").show() : g()(t.target).nextAll("span").hide()
                    }
                }
            },
            x = {
                render: function () {
                    var t = this,
                        e = t.$createElement,
                        i = t._self._c || e;
                    return i("div", {
                        staticClass: "preview",
                        attrs: {
                            id: "main"
                        }
                    }, [i("audio", {
                        class: {
                            play: t.isPlay
                        },
                        attrs: {
                            id: "audio2",
                            loop: "loop",
                            autoplay: "autoplay",
                            src: t.music.url
                        }
                    }), t._v(" "), i("div", {
                        staticClass: "workwrap"
                    }, [i("div", {
                        staticClass: "workcont",
                        style: "height: " + t.height + "px"
                    }, [t.isMusic ? i("a", {
                        staticClass: "musicbtn",
                        class: {
                            play: t.isPlay
                        },
                        style: "width:" + 30 * this.scaleWidth + "px;height:" + 30 * this.scaleWidth + "px;\n                top:" + 20 * this.scaleWidth + "px;right:" + 20 * this.scaleWidth + "px;",
                        on: {
                            click: t.playOrPauseMusic
                        }
                    }) : t._e(), t._v(" "), i("div", {
                        staticClass: "mainarea canvas",
                        style: {
                            height: this.canvasHeight,
                            width: this.canvasWidth,
                            position: "relative"
                        }
                    }, [i("div", {
                        staticClass: "bgCanvas",
                        style: t.setBgStyle()
                    }), t._v(" "), i("div", {
                        staticClass: "elementCanvas",
                        staticStyle: {
                            "transform-origin": "0 0",
                            transform: "scale(0.5)"
                        }
                    }, t._l(t.sceneInfo.content, function (e, s) {
                        return i("div", {
                            key: s
                        }, ["img" == e.type ? i("div", {
                            staticClass: "img-element"
                        }, [i("div", {
                            staticClass: "wrapper",
                            style: t.setWrapperStyle(e, s),
                            on: {
                                contextmenu: function (e) {
                                    e.preventDefault(), t.showMenu(s)
                                }
                            }
                        }, [i("img", {
                            staticClass: "img-render render",
                            style: t.setRenderStyle(e),
                            attrs: {
                                src: t.showimg(e, e.style.width)
                            }
                        })])]) : t._e(), t._v(" "), "text" == e.type ? i("div", {
                            staticClass: "text-element",
                            attrs: {
                                id: s
                            }
                        }, [i("div", {
                            staticClass: "wrapper",
                            style: t.setWrapperStyle(e, s),
                            on: {
                                contextmenu: function (e) {
                                    e.preventDefault(), t.showMenu(s)
                                }
                            }
                        }, [i("div", {
                            staticClass: "text-render render",
                            style: t.setRenderStyle(e)
                        }, [i("div", {
                            staticClass: "scaleArea",
                            staticStyle: {
                                height: "100%"
                            },
                            style: t.setFontScale()
                        }, [i("div", {
                            staticClass: "vue-edit-area",
                            style: t.setTextStyle(e),
                            attrs: {
                                danyeid: s
                            },
                            domProps: {
                                innerHTML: t._s(e.content)
                            }
                        }, [i("i")])])])])]) : t._e(), t._v(" "), "map" == e.type ? i("div", {
                            staticClass: "map-element"
                        }, [i("div", {
                            staticClass: "wrapper",
                            style: t.setWrapperStyle(e, s),
                            on: {
                                contextmenu: function (e) {
                                    e.preventDefault(), t.showMenu(s)
                                }
                            }
                        }, [i("div", {
                            staticClass: "map-render render",
                            style: t.setRenderStyle(e)
                        }, [i("img", {
                            staticStyle: {
                                width: "100%",
                                height: "100%"
                            },
                            attrs: {
                                title: "双击可修改地址",
                                src: "http://api.map.baidu.com/staticimage/v2?ak=WtfAdHwd1tMOCf2dzdRIhNZkSq8V7o5W&width=600&height=300&dpiType=ph&markers=" + e.address + "|" + e.location[1] + "," + e.location[0] + "&markerStyles=l,,0xff0000&center=" + e.address + "&labels=" + e.address + "|" + e.location[1] + "," + e.location[0] + "&zoom=17&labelStyles=我在这,1,28,0xffffff,0x1abd9b,1"
                            }
                        }), t._v(" "), i("a", {
                            staticStyle: {
                                width: "80px",
                                height: "30px",
                                "line-height": "30px",
                                background: "rgb(26, 189, 155)",
                                position: "absolute",
                                right: "10px",
                                bottom: "10px",
                                color: "rgb(255, 255, 255)",
                                "text-align": "center",
                                "border-radius": "3px"
                            },
                            on: {
                                click: function (i) {
                                    t.goLocation(e)
                                }
                            }
                        }, [t._v("导航")])])])]) : t._e(), t._v(" "), "linkButton" == e.type ? i("div", {
                            staticClass: "link-button-element"
                        }, [i("div", {
                            staticClass: "wrapper",
                            style: t.setWrapperStyle(e, s),
                            on: {
                                contextmenu: function (e) {
                                    e.preventDefault(), t.showMenu(s)
                                }
                            }
                        }, [i("div", {
                            staticClass: "link-button-render render",
                            style: t.setRenderStyle(e)
                        }, [0 == e.isImg ? i("div", {
                            staticClass: "text1 link-button-render-text"
                        }, [i("a", {
                            staticClass: "telebtn",
                            style: t.setWrapperStyle2(e, s),
                            attrs: {
                                href: t.setUrl(e)
                            }
                        }, ["" != e.tel ? i("i", {
                            staticClass: "iconfont icon-bohao",
                            style: t.setBtnStyle(e.style)
                        }) : t._e(), t._v(" " + t._s(e.text))])]) : t._e(), t._v(" "), e.isImg ? i("img", {
                            attrs: {
                                src: e.pic.url
                            }
                        }) : t._e()])])]) : t._e(), t._v(" "), "interActionButton" == e.type ? i("div", {
                            staticClass: "link-button-element"
                        }, [i("div", {
                            staticClass: "wrapper",
                            style: t.setWrapperStyle(e, s),
                            on: {
                                contextmenu: function (e) {
                                    e.preventDefault(), t.showMenu(s)
                                }
                            }
                        }, [i("div", {
                            staticClass: "link-button-render render",
                            style: t.setRenderStyle(e)
                        }, [0 == e.isImg ? i("div", {
                            staticClass: "text1 link-button-render-text"
                        }, [i("a", {
                            staticClass: "zankbtn",
                            class: t.getZanCount(e),
                            style: t.setWrapperStyle2(e, s),
                            attrs: {
                                id: e.id
                            },
                            on: {
                                click: function (i) {
                                    t.submitZan(e)
                                }
                            }
                        }, [i("i", {
                            staticClass: "iconfont icon-dianzan1",
                            style: t.setBtnStyle(e.style)
                        }), t._v("  "), i("span", [t._v(t._s(e.text))])])]) : t._e(), t._v(" "), e.isImg ? i("img", {
                            attrs: {
                                src: e.pic.url
                            }
                        }) : t._e()])])]) : t._e(), t._v(" "), "countDown" == e.type ? i("div", {
                            staticClass: "link-button-element"
                        }, [i("div", {
                            staticClass: "wrapper",
                            style: t.setWrapperStyle(e, s),
                            on: {
                                contextmenu: function (e) {
                                    e.preventDefault(), t.showMenu(s)
                                }
                            }
                        }, [i("div", {
                            staticClass: "link-button-render render",
                            style: t.setRenderStyle(e)
                        }, [i("countdownbox", {
                            attrs: {
                                startTime: (new Date).getTime(),
                                endText: e.endtip,
                                endTime: parseInt(e.deadlineTime),
                                item: e,
                                scale: t.scaleWidth
                            }
                        })], 1)])]) : t._e(), t._v(" "), "shape" == e.type ? i("div", {
                            staticClass: "shape-element"
                        }, [i("div", {
                            staticClass: "wrapper",
                            style: t.setWrapperStyle(e, s),
                            on: {
                                contextmenu: function (e) {
                                    e.preventDefault(), t.showMenu(s)
                                }
                            }
                        }, [i("div", {
                            staticClass: "shape-render render",
                            style: t.setRenderStyle(e)
                        }, [i("div", {
                            staticClass: "svg",
                            class: "svg1_" + s,
                            attrs: {
                                "data-flag": "0"
                            },
                            domProps: {
                                innerHTML: t._s(t.setShapeHtml1(e, s))
                            }
                        })])])]) : t._e(), t._v(" "), "new-form" == e.type ? i("div", {
                            staticClass: "new-form-element"
                        }, [i("div", {
                            staticClass: "wrapper",
                            style: t.setWrapperStyle(e, s),
                            on: {
                                contextmenu: function (e) {
                                    e.preventDefault(), t.showMenu(s)
                                }
                            }
                        }, [i("div", {
                            staticClass: "new-form-render render",
                            style: t.setRenderStyle(e)
                        }, [t._l(e.options, function (s, n) {
                            return i("section", {
                                key: n,
                                class: s.oType
                            }, [i("label", {
                                class: {
                                    required: s.requiredFlag
                                },
                                style: t.setFormTextSize1(e),
                                domProps: {
                                    textContent: t._s(s.name)
                                }
                            }, [i("span", {
                                staticClass: "error-tip"
                            }, [t._v(t._s(s.placeholder))])]), t._v(" "), "textfield" == s.oType ? i("input", {
                                staticStyle: {
                                    border: "1px solid"
                                },
                                style: t.setInputHeight(e, s.oType),
                                attrs: {
                                    id: "input" + n,
                                    placeholder: s.placeholder
                                }
                            }) : "textbox" == s.oType ? i("textarea", {
                                staticStyle: {
                                    border: "1px solid"
                                },
                                style: t.setInputHeight(e, s.oType),
                                attrs: {
                                    id: "input" + n,
                                    placeholder: s.placeholder
                                }
                            }) : t._e()])
                        }), t._v(" "), i("div", {
                            staticClass: "submit-btn",
                            style: t.setSubmitHeight(e),
                            on: {
                                click: function (i) {
                                    t.formBtnClick(e)
                                }
                            }
                        }, [t._v(t._s(e.btnName) + " ")])], 2)])]) : t._e(), t._v(" "), "i" == e.type && 1 != e.is_del ? i("div", {
                            staticClass: "new-form-element"
                        }, [i("div", {
                            staticClass: "wrapper",
                            style: t.setWrapperStyle(e, s),
                            on: {
                                contextmenu: function (e) {
                                    e.preventDefault(), t.showMenu(s)
                                }
                            }
                        }, [i("div", {
                            staticClass: "new-form-render render"
                        }, [i("div", {
                            staticClass: "forminputbox",
                            style: t.setFormTextSize(e)
                        }, [i("input", {
                            staticStyle: {
                                display: "block",
                                position: "absolute",
                                margin: "0",
                                padding: "0",
                                border: "0"
                            },
                            style: t.setTextStyle(e),
                            attrs: {
                                type: "text",
                                id: e.id
                            },
                            on: {
                                focus: t.inputfocus,
                                blur: t.inputblur
                            }
                        }), t._v(" "), i("span", {
                            staticStyle: {
                                "padding-left": "10px"
                            },
                            style: t.setSpanStyle(e.style)
                        }, [t._v(t._s(e.placeholder))]), t._v(" "), "1" == e.require ? i("span", {
                            style: t.setSpanStyle(e.style)
                        }, [t._v("*")]) : t._e()])])])]) : t._e(), t._v(" "), "s" == e.type && 1 != e.is_del ? i("div", {
                            staticClass: "shape-element"
                        }, [i("div", {
                            staticClass: "wrapper",
                            staticStyle: {
                                "text-align": "center"
                            },
                            style: t.setWrapperStyle(e, s),
                            on: {
                                contextmenu: function (e) {
                                    e.preventDefault(), t.showMenu(s)
                                }
                            }
                        }, [i("div", {
                            staticClass: "new-form-render render"
                        }, [i("div", {
                            staticClass: "forminputbox",
                            style: t.setTextStyle(e),
                            on: {
                                click: function (i) {
                                    t.submitData(e, t.sceneInfo.content, i)
                                }
                            }
                        }, [t._v("\n                  " + t._s(e.str) + "\n                ")])])])]) : t._e(), t._v(" "), "r" == e.type && 1 != e.is_del ? i("div", {
                            staticClass: "shape-element"
                        }, [i("div", {
                            staticClass: "wrapper",
                            style: t.setWrapperStyle(e, s),
                            on: {
                                contextmenu: function (e) {
                                    e.preventDefault(), t.showMenu(s)
                                }
                            }
                        }, [i("div", {
                            staticClass: "submit-render render"
                        }, ["r" == e.type ? i("div", {
                            staticClass: "radiogroup",
                            staticStyle: {
                                "border-radius": "6px",
                                backgroundColor: "#fff"
                            },
                            style: t.setTextStyle(e)
                        }, [i("p", {
                            style: t.setRadioTitle(e, s)
                        }, [t._v(t._s(e.str))]), t._v(" "), i("div", {
                            staticClass: "groupwrap"
                        }, [i("ul", {
                            staticStyle: {
                                "background-color": "#fff"
                            },
                            style: t.setRadioStyle(e, s),
                            attrs: {
                                id: e.id
                            }
                        }, t._l(e.value, function (s) {
                            return i("li", {
                                key: s
                            }, [i("input", {
                                attrs: {
                                    type: "radio",
                                    name: e.str
                                },
                                domProps: {
                                    value: s
                                }
                            }), i("span", [t._v(t._s(s))])])
                        }))])]) : t._e()])])]) : t._e(), t._v(" "), "c" == e.type && 1 != e.is_del ? i("div", {
                            staticClass: "shape-element"
                        }, [i("div", {
                            staticClass: "wrapper",
                            style: t.setWrapperStyle(e, s),
                            on: {
                                contextmenu: function (e) {
                                    e.preventDefault(), t.showMenu(s)
                                }
                            }
                        }, [i("div", {
                            staticClass: "submit-render render"
                        }, ["c" == e.type ? i("div", {
                            staticClass: "radiogroup",
                            staticStyle: {
                                "border-radius": "6px",
                                backgroundColor: "#fff"
                            },
                            style: t.setTextStyle(e)
                        }, [i("p", {
                            style: t.setRadioTitle(e, s)
                        }, [t._v(t._s(e.str))]), t._v(" "), i("div", {
                            staticClass: "groupwrap"
                        }, [i("ul", {
                            staticStyle: {
                                "background-color": "#fff"
                            },
                            style: t.setRadioStyle(e, s),
                            attrs: {
                                id: e.id
                            }
                        }, t._l(e.value, function (s) {
                            return i("li", {
                                key: s
                            }, [i("input", {
                                attrs: {
                                    type: "checkbox",
                                    name: e.str
                                },
                                domProps: {
                                    value: s
                                }
                            }), i("span", [t._v(t._s(s))])])
                        }))])]) : t._e()])])]) : t._e(), t._v(" "), "p" == e.type && 1 != e.is_del ? i("div", {
                            staticClass: "shape-element"
                        }, [i("div", {
                            staticClass: "wrapper",
                            style: t.setWrapperStyle(e, s),
                            on: {
                                contextmenu: function (e) {
                                    e.preventDefault(), t.showMenu(s)
                                }
                            }
                        }, [i("div", {
                            staticClass: "submit-render render"
                        }, ["p" == e.type ? i("div", {
                            staticClass: "selectgroup"
                        }, [i("select", {
                            staticStyle: {
                                padding: "6px 10px"
                            },
                            style: t.setRadioStyle(e, s),
                            attrs: {
                                id: e.id
                            }
                        }, [i("option", {
                            attrs: {
                                value: ""
                            }
                        }, [t._v(t._s(e.str))]), t._v(" "), t._l(e.value, function (e) {
                            return i("option", {
                                key: e
                            }, [t._v(t._s(e))])
                        })], 2)]) : t._e()])])]) : t._e()])
                    }))])])])])
                }, staticRenderFns: []
            };
        var S = i("VU/8")(w, x, !1, function (t) {
                i("0byr")
            }, null, null).exports,
            b = i("/ocq");
        a.default.use(b.a);
        var _, C = new b.a({
                mode: "history",
                routes: [{
                    path: "/",
                    name: "main",
                    component: S
                }]
            }),
            k = i("bOdI"),
            I = i.n(k),
            W = function (t, e) {
                (0, t.commit)("INITUSERDATA", e)
            },
            T = function (t, e) {
                (0, t.commit)("INITORIGINDATA", e)
            },
            z = function (t) {
                return t.originData.lists
            },
            M = function (t) {
                return t.originData.music
            },
            D = function (t) {
                return t.originData.singleInfo
            },
            $ = function (t) {
                return t.originData.userinfo
            },
            R = function (t) {
                return t.originData.additional
            };
        a.default.use(r.a);
        var H = (_ = {}, I()(_, "INITUSERDATA", function (t, e) {
                t.originData.userinfo = e
            }), I()(_, "INITORIGINDATA", function (t, e) {
                t.originData.lists = e.lists, t.originData.additional = e.additional, t.originData.singleInfo = e.singleinfo, t.originData.music = e.lists.music
            }), _),
            A = new r.a.Store({
                actions: s,
                getters: n,
                mutations: H,
                state: {
                    originData: {
                        lists: {},
                        userinfo: {},
                        additional: {},
                        singleInfo: {},
                        music: {}
                    }
                },
                strict: !1
            }),
            N = i("mtWM"),
            O = i.n(N),
            q = i("BTaQ"),
            B = i.n(q);
        i("+skl");
        a.default.config.productionTip = !1, a.default.use(r.a), a.default.use(B.a), O.a.defaults.withCredentials = !0, a.default.prototype.$http = O.a, new a.default({
            el: "#main",
            router: C,
            store: A,
            components: {
                Main: S
            },
            template: "<Main/>"
        })
    }
}, ["NHnr"]);
