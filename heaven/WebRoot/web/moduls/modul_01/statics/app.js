webpackJsonp([1], {
        "+skl": function(t, e) {},
        "14xJ": function(t, e, i) {
            "use strict"; (function(t) {
                var s = i("gRE1"),
                    n = i.n(s),
                    r = i("Dd8w"),
                    a = i.n(r),
                    o = i("gyMJ"),
                    l = i("NYxO"),
                    d = i("EdQI"),
                    c = {};
                e.a = {
                    name: "main-page",
                    data: function() {
                        var t = this;
                        return {
                            pageSwitch: {},
                            isPlay: !0,
                            h5Code: "",
                            scaleWidth: 0,
                            scaleHeight: 0,
                            isChange: !1,
                            resImg: 0,
                            top: 0,
                            flag: !0,
                            scrollIndex: 0,
                            pageStr: "",
                            is_page: !1,
                            effectList: [{
                                value: "slide",
                                label: "位移切换"
                            },
                                {
                                    value: "fade",
                                    label: "淡入"
                                },
                                {
                                    value: "cube",
                                    label: "方块"
                                },
                                {
                                    value: "coverflow",
                                    label: "3d流"
                                },
                                {
                                    value: "flip",
                                    label: "3d翻转"
                                }],
                            height: 0,
                            swiperOption: {
                                notNextTick: !0,
                                autoplayDisableOnInteraction: !1,
                                direction: "vertical",
                                autoplay: this.getSettingParam("autoplay"),
                                loop: !1,
                                grabCursor: !0,
                                setWrapperSize: !0,
                                autoHeight: !0,
                                paginationClickable: !0,
                                pagination: ".swiper-pagination",
                                paginationType: "progress",
                                speed: 300,
                                observer: !0,
                                observeParents: !0,
                                effect: this.getSettingParam("page_mode"),
                                mousewheelControl: !0,
                                debugger: !0,
                                autoplayStopOnLast: !0,
                                onInit: function(e) {
                                    c = e,
                                        t.scrollIndex = e.realIndex,
                                        t.initSwiper(e),
                                        t.pageStr = e.realIndex + 1 + "/" + t.pagesData.length
                                },
                                onSlideChangeStart: function(t) {
                                    c = t
                                },
                                onTransitionStart: function(e) {
                                    t.scrollIndex = e.realIndex,
                                        t.pageStr = e.realIndex + 1 + "/" + t.pagesData.length
                                }
                            }
                        }
                    },
                    computed: a()({},
                        Object(l.b)(["pagesData", "currentPageBg", "sceneInfoSub", "additional", "currentPage"])),
                    components: {
                        countdownbox: d.a
                    },
                    mounted: function() {
                        this.is_page = 1 == this.sceneInfoSub.is_page,
                            t(window).resize(function() {
                                this.top = parseInt((t(window).height() - 504 * this.scaleWidth) / 2)
                            })
                    },
                    created: function() {
                        this.height = t(window).height(),
                            this.scaleWidth = t(window).width() / 320,
                            this.scaleHeight = t(window).height() / 504,
                            this.top = parseInt((t(window).height() - 504 * this.scaleWidth) / 2)
                    },
                    methods: {
                        goLocation: function(t) {
                            this.oldMap(t)
                        },
                        newMap: function(t) {
                            var e = this,
                                i = this;
                            AMap.plugin("AMap.Geolocation",
                                function() { (new AMap.Geolocation).getCurrentPosition(function(s, n) {
                                    if (1 == n.status && "SUCCESS" === n.info) {
                                        if (n) {
                                            var r = {};
                                            r.start = {
                                                address: n.formattedAddress,
                                                lat: n.position.lat,
                                                lng: n.position.lng
                                            },
                                                r.end = {
                                                    address: t.address,
                                                    lat: t.lat,
                                                    lng: t.lng
                                                },
                                                i.navicationNew(r)
                                        }
                                    } else e.$Message.error("failed" + e.getStatus())
                                })
                                })
                        },
                        navicationNew: function(t) {
                            window.parent.location.href = "https://uri.amap.com/navigation?from=" + t.start.lng + "," + t.start.lat + "," + t.start.address + "&to=" + t.end.lng + "," + t.end.lat + "," + t.end.address + "&mode=car&policy=0"
                        },
                        oldMap: function(t) {
                            var e = this,
                                i = new BMap.Geolocation,
                                s = new BMap.Geocoder;
                            i.getCurrentPosition(function(i) {
                                    this.getStatus() == BMAP_STATUS_SUCCESS ? s.getLocation(i.point,
                                        function(s) {
                                            if (s) {
                                                new BMap.Marker(i.point);
                                                e.lng = i.point.lng,
                                                    e.lat = i.point.lat;
                                                var n = {};
                                                n.start = {
                                                    address: s.surroundingPois[0].address,
                                                    msg: s.addressComponents,
                                                    lat: i.point.lat,
                                                    lng: i.point.lng
                                                },
                                                    n.end = {
                                                        address: t.address,
                                                        lat: t.lat,
                                                        lng: t.lng
                                                    },
                                                    e.navicationOld(n)
                                            }
                                        }) : this.$Message.error("failed" + this.getStatus())
                                },
                                {
                                    enableHighAccuracy: !0
                                })
                        },
                        navicationOld: function(t) {
                            window.parent.location.href = "http://api.map.baidu.com/direction?origin=latlng:" + t.start.lat + "," + t.start.lng + "|name:" + t.start.address + "&destination=" + t.end.address + "&mode=driving&region=" + t.start.msg.province + "&output=html&src=yourCompanyName|yourAppName"
                        },
                        setTop: function(t) {
                            var e = "";
                            return e += "position: relative;top:" + this.top + "px;"
                        },
                        exit: function(t) {
                            exitPages(this.sceneInfoSub.id, 1)
                        },
                        playOrPauseMusic: function() {
                            var t = document.getElementById("audio2");
                            t.paused ? (t.play(), this.isPlay = !0) : (this.isPlay = !1, t.pause())
                        },
                        initSwiper: function(t) {
                            this.flag && (this.flag = !1)
                        },
                        getSettingParam: function(t) {
                            var e = this.$store.state.originData.sceneinfo;
                            if (void 0 !== e) {
                                if ("page_mode" != t) return "autoplay" == t ? 1e3 * e[t] : 0 != e[t];
                                switch (e[t]) {
                                    case 1:
                                        return "slide";
                                    case 2:
                                        return "fade";
                                    case 3:
                                        return "cube";
                                    case 4:
                                        return "coverflow";
                                    case 5:
                                        return "flip"
                                }
                            }
                        },
                        setBgStyle: function(t) {
                            var e = t.pagebg;
                            return void 0 === e ? "background: #fff": void 0 === e.backgroundImage ? "background: #fff": e.backgroundImage.length ? "background: url(" + e.backgroundImage + ") no-repeat center;background-size: 100% 100%;": "background: " + e.backgroundColor
                        },
                        setOutStyle: function(t, e, i) {
                            if (3 != i) {
                                var s = "",
                                    n = !1;
                                for (var r in e) {
                                    var a = e[r];
                                    if (4 != i) if ("width" === r) {
                                        if (0 == isNaN(a)) {
                                            s += r + ":" + a * this.scaleWidth + "px;";
                                            continue
                                        }
                                        if ( - 1 == a.indexOf("px")) {
                                            s += r + ":" + a * this.scaleWidth + "px;";
                                            continue
                                        }
                                        s += r + ":" + a * this.scaleWidth + ";"
                                    } else if ("height" === r) {
                                        if (0 == isNaN(a)) {
                                            s += r + ":" + a * this.scaleWidth + "px;",
                                                s += "lineHeight:" + a * this.scaleWidth + "px;";
                                            continue
                                        }
                                        if ( - 1 == a.indexOf("px")) {
                                            s += r + ":" + a * this.scaleWidth + "px;";
                                            continue
                                        }
                                        s += r + ":" + a * this.scaleWidth + "px;"
                                    }
                                    if ("fontSize" === r) s += r + ":" + (a = "string" == typeof a ? parseInt(a) : a) * this.scaleWidth + "px;";
                                    else if ("top" === r) {
                                        if (0 == isNaN(a)) {
                                            s += r + ":" + a * this.scaleWidth + "px;";
                                            continue
                                        }
                                        if ( - 1 == a.indexOf("px")) {
                                            s += r + ":" + a * this.scaleWidth + "px;";
                                            continue
                                        }
                                        s += r + ":" + a * this.scaleWidth + ";"
                                    } else if ("left" === r) {
                                        if (0 == isNaN(a)) {
                                            s += r + ":" + a * this.scaleWidth + "px;";
                                            continue
                                        }
                                        if ( - 1 == a.indexOf("px")) {
                                            s += r + ":" + a * this.scaleWidth + "px;";
                                            continue
                                        }
                                        s += r + ":" + a * this.scaleWidth + ";"
                                    } else if ("color" === r && "interActionButton" === i) s += r + ":#ffffff;";
                                    else if ("word-wrap" == r) n = !0;
                                    else {
                                        if ("lineHeight" == r) continue;
                                        s += "borderRadius" == r ? r + ":" + a + "px;": r + ":" + a + ";"
                                    }
                                }
                                return 4 == i && (s += "width:" + t.cropData.width * this.scaleWidth + "px;height:" + t.cropData.height * this.scaleWidth + "px;"),
                                n || (s += "word-wrap:break-word"),
                                    s
                            }
                        },
                        setInStyle: function(t, e) {
                            var i = t. in .css;
                            if (3 != e) {
                                var s = "",
                                    n = !1;
                                for (var r in i) {
                                    var a = i[r];
                                    if ("width" === r) {
                                        if (0 == isNaN(a)) {
                                            s += r + ":" + a * this.scaleWidth + "px;";
                                            continue
                                        }
                                        s += r + ":" + a * this.scaleWidth + ";"
                                    } else if ("height" === r) {
                                        if (0 == isNaN(a)) {
                                            s += r + ":" + a * this.scaleWidth + "px;",
                                                s += "lineHeight:" + a * this.scaleWidth + "px;";
                                            continue
                                        }
                                        s += "lineHeight:" + a * this.scaleWidth + "px;",
                                            s += r + ":" + a * this.scaleWidth + "px;"
                                    } else if ("top" === r) {
                                        if (0 == isNaN(a)) {
                                            s += r + ":" + a * this.scaleWidth + "px;";
                                            continue
                                        }
                                        s += r + ":" + a * this.scaleWidth + ";"
                                    } else if ("left" === r) {
                                        if (0 == isNaN(a)) {
                                            s += r + ":" + a * this.scaleWidth + "px;";
                                            continue
                                        }
                                        s += r + ":" + a * this.scaleWidth + ";"
                                    } else if ("backgroundColor" === r) {
                                        if ("countDown" == t.type) {
                                            s += r + ":#fff";
                                            continue
                                        }
                                        s += "" == r ? r + ":rgb(0,0,0)": r + ":" + a + ";"
                                    } else if ("borderRadius" === r) s += r + ":" + (a = "string" == typeof a ? parseInt(a) : a) * this.scaleWidth + "px;";
                                    else if ("word-wrap" == r) n = !0;
                                    else if ("fontSize" == r) s += r + ":" + (a = "string" == typeof a ? parseInt(a) : a) * this.scaleWidth + "px;";
                                    else {
                                        if ("backgroundImage" == r) continue;
                                        if ("backgroundSize" == r) continue;
                                        if ("backgroundPosition" == r) continue;
                                        if ("borderWidth" == r) continue;
                                        if ("borderStyle" == r) continue;
                                        if ("borderColor" == r) continue;
                                        if ("lineHeight" == r) continue;
                                        if (0 == r.indexOf("padding")) continue;
                                        s += r + ":" + a + ";"
                                    }
                                }
                                return n || (s += "word-wrap:break-word"),
                                    s
                            }
                        },
                        setFormTextSize: function(t) {
                            var e = void 0 === t. in .css.fontSize ? 15 : t. in .css.fontSize,
                                i = "fontSize:" + e * this.scaleWidth + "px;height:" + 2 * e * this.scaleHeight + "px;line-height: " + 2 * e * this.scaleHeight + "px;";
                            return i += "color :" + t. in .css.color + ";"
                        },
                        setSpanStyle: function(t) {
                            var e = "";
                            for (var i in t) {
                                var s = t[i];
                                "height" === i ? e += i + ":" + s * this.scaleWidth + "px;": "lineHeight" === i ? e += i + ":" + parseInt(s) * this.scaleWidth + "px;": "fontSize" === i ? e += i + ":" + parseInt(s) * this.scaleWidth + "px;": "color" === i && (e += i + ":" + s + ";")
                            }
                            return e
                        },
                        isHide: function(t) {
                            return t == this.scrollIndex ? "current": "item"
                        },
                        setBtnStyle: function(t) {
                            var e = "";
                            for (var i in t) {
                                var s = t[i];
                                "fontSize" === i && (e += i + ":" + (s = "string" == typeof s ? parseInt(s) : s) * this.scaleWidth + "px;")
                            }
                            return e
                        },
                        setOutImageStyle: function(t, e, i) {
                            var s = "";
                            for (var n in e) {
                                var r = e[n];
                                if ("top" === n) {
                                    if (0 == isNaN(r)) {
                                        s += n + ":" + r * this.scaleWidth + "px;";
                                        continue
                                    }
                                    s += n + ":" + r * this.scaleWidth + ";"
                                } else if ("left" === n) {
                                    if (0 == isNaN(r)) {
                                        s += n + ":" + r * this.scaleWidth + "px;";
                                        continue
                                    }
                                    s += n + ":" + r * this.scaleWidth + ";"
                                } else "borderRadius" === n ? s += n + ":" + r * this.scaleWidth + "px;": "borderStyle" == n ? s += n + ":" + r + ";": "borderColor" == n ? s += n + ":" + r + ";": "borderWidth" == n && (s += n + ":" + (r = "string" == typeof r ? parseInt(r) : r) + "px;")
                            }
                            s += "width:" + t.cropData.width * this.scaleWidth + "px;height:" + parseInt(t.cropData.height) * this.scaleWidth + "px;";
                            var a = t.src;
                            return - 1 != a.indexOf("http://") && (a = a.replace("http://", "https://")),
                            void 0 == e.borderWidth && (e.borderWidth = 0),
                                s += "backgroundImage:url(" + a + ");backgroundSize: " + e.width * this.scaleWidth + "px " + e.height * this.scaleWidth + "px;background-position: -" + (t.cropData.x + e.borderWidth) * this.scaleWidth + "px -" + (t.cropData.y + e.borderWidth) * this.scaleWidth + "px;",
                                s += "word-wrap:break-word"
                        },
                        setTextStyle: function(t) {
                            var e = t. in .css,
                                i = "";
                            for (var s in e) {
                                var n = e[s];
                                if ("width" !== s) if ("height" === s) i += s + ":" + n * this.scaleWidth + "px;";
                                else if ("lineHeight" === s) i += s + ":" + n + ";";
                                else {
                                    if ("fontFamily" === s) {
                                        i += s + ":" + n + ";";
                                        continue
                                    }
                                    if ("fontSize" === s) {
                                        i += s + ":" + parseInt(n) * this.scaleWidth + "px;";
                                        continue
                                    }
                                    if ("color" === s) i += s + ":" + n + ";";
                                    else if ("letterSpacing" === s) i += s + ":" + parseInt(n) * this.scaleWidth + "px;";
                                    else {
                                        if ("borderWidth" == s) {
                                            i += s + ":" + n + "px;";
                                            continue
                                        }
                                        if ("borderStyle" == s) {
                                            i += s + ":" + n + ";";
                                            continue
                                        }
                                        if ("borderColor" == s) {
                                            i += s + ":" + n + ";";
                                            continue
                                        }
                                        if ("textDecoration" == s) {
                                            i += s + ":" + n + ";";
                                            continue
                                        }
                                        if ("borderRadius" == s) {
                                            i += s + ":" + n + "px;";
                                            continue
                                        } - 1 != s.indexOf("padding") && (i += "paddingBottom" == s || "paddingTop" == s ? s + ":" + parseFloat(n) + "em;": s + ":" + parseFloat(n) + "px;")
                                    }
                                } else i += s + ":" + n * this.scaleWidth + "px;"
                            }
                            return i
                        },
                        setTextStyle1: function(t) {
                            var e = t.out.css.width,
                                i = t.out.css.height,
                                s = t. in .css.fontFamily,
                                n = t. in .css.fontSize,
                                r = t. in .css.color,
                                a = t.out.css.height,
                                o = t. in .css.letterSpacing,
                                l = t. in .css.textDecoration,
                                d = t. in .css.backgroundColor,
                                c = t. in .css.borderStyle,
                                p = t. in .css.borderColor,
                                u = "string" == typeof t. in .css.borderWidth ? parseInt(t. in .css.borderWidth) : t. in .css.borderWidth,
                                h = "string" == typeof t. in .css.borderRadius ? parseInt(t. in .css.borderRadius) : t. in .css.borderRadius;
                            return "color:" + r + ";width:" + e * this.scaleWidth + "px;height:" + i * this.scaleWidth + "px;fontFamily:" + s + ";fontSize:" + parseInt(n) * this.scaleWidth + "px;textDecoration:" + l + ";backgroundColor:" + d + ";borderWidth:" + u + "px;borderStyle:" + c + ";borderColor:" + p + ";borderRadius:" + h + "px;lineHeight:" + a * this.scaleWidth + "px;letterSpacing:" + o + "px;"
                        },
                        setRenderStyle: function(t) {
                            var e = t. in .css,
                                i = "";
                            for (var s in e) {
                                var n = e[s];
                                if (4 != t.type && ("width" === s && (i += s + ":" + parseInt(n * this.scaleWidth) + "px;", "img" == t.type && (i += "background-size:100% 100%;")), "height" === s && (i += s + ":" + parseInt(n * this.scaleWidth) + "px;", "countDown" == t.type && (i += "line-height:" + n + "px;"))), "rotate" === s && (i += "transform:rotate(" + n + "deg);"), "opacity" === s && (i += s + ":" + (1 - n) + ";"), "line-height" === s && (i += s + ":" + n + ";"), "color" === s && (i += s + ":" + n + ";"), "fontSize" === s && (i += s + ":" + n + "px;"), "textAlign" === s && (i += s + ":" + n + ";"), "linkButton" == t.type) t.isImg ? "backgroundColor" !== s && "background-color" !== s || (i += s + ":transparent;") : "backgroundColor" !== s && "background-color" !== s || (i += s + ":" + n + ";");
                                else if ("backgroundColor" === s || "background-color" === s) {
                                    if ("countDown" == t.type) {
                                        i += s + ":inherit";
                                        continue
                                    }
                                    i += "" == s ? s + ":rgb(0,0,0)": s + ":" + n + ";"
                                }
                                "letterSpacing" === s && (i += s + ":" + n + "px;"),
                                "lineHeight" === s && (i += s + ":" + n + ";"),
                                "fontWeight" === s && (i += s + ":" + n + ";"),
                                "fontStyle" === s && (i += s + ":" + n + ";"),
                                "textDecoration" === s && (i += s + ":" + n + ";"),
                                "writingMode" === s && (i += s + ":" + n + ";"),
                                "borderRadius" === s && (i += s + ":" + (n = "string" == typeof n ? parseInt(n) : n) + "px;")
                            }
                            return i
                        },
                        setRadioStyle: function(t, e) {
                            var i = t. in .css,
                                s = "";
                            for (var n in i) {
                                var r = i[n];
                                if ("width" === n) {
                                    if (0 == isNaN(r)) {
                                        s += n + ":" + r + "px;";
                                        continue
                                    }
                                    s += n + ":" + r + ";"
                                } else {
                                    if ("height" === n) continue;
                                    if ("top" === n) {
                                        if (0 == isNaN(r)) {
                                            s += n + ":" + r + "px;";
                                            continue
                                        }
                                        s += n + ":" + r + ";"
                                    } else if ("left" === n) {
                                        if (0 == isNaN(r)) {
                                            s += n + ":" + r + "px;";
                                            continue
                                        }
                                        s += n + ":" + r + ";"
                                    } else if ("background" === n)"g" != t.type ? s += "borderColor:" + r + ";": s += n + ":" + r + ";";
                                    else if ("backgroundColor" === n)"g" != t.type ? s += "borderColor:" + r + ";": s += n + ":" + r + ";";
                                    else {
                                        if ("borderRadius" === n) continue;
                                        if ("borderColor" === n) {
                                            if ("g" != t.type) continue;
                                            s += n + ":" + r + ";"
                                        } else if ("fontSize" == n) s += n + ":" + (r = "string" == typeof r ? parseInt(r) : r) + "px;";
                                        else if ("borderWidth" == n) s += n + ":" + (r = "string" == typeof r ? parseInt(r) : r) + "px;";
                                        else if ("lineHeight" == n) s += n + ":" + r + "px;";
                                        else {
                                            if (0 == n.indexOf("padding")) continue;
                                            s += n + ":" + r + ";"
                                        }
                                    }
                                }
                            }
                            return s
                        },
                        setOptionStyle: function(t, e) {
                            var i = t. in .css;
                            if (3 != e) {
                                var s = "";
                                for (var n in i) {
                                    var r = i[n];
                                    "fontSize" == n && (s += n + ":" + .5 * (r = "string" == typeof r ? parseInt(r) : r) + "px;")
                                }
                                return s
                            }
                        },
                        setRadioTitle: function(t, e) {
                            var i = t. in .css,
                                s = "";
                            for (var n in i) {
                                var r = i[n];
                                if ("background" === n) s += n + ":" + r + ";";
                                else if ("backgroundColor" == n) s += n + ":" + r + ";";
                                else if ("fontSize" == n) s += n + ":" + (r = "string" == typeof r ? parseInt(r) : r) + "px;";
                                else if ("lineHeight" == n) s += n + ":" + r + "px;";
                                else if ("fontFamily" == n) s += n + ":" + r + ";";
                                else {
                                    if ("color" != n) continue;
                                    s += n + ":" + r + ";"
                                }
                            }
                            return s
                        },
                        setNewAni: function(t, e) {
                            if (0 != t.length) {
                                for (var i = "",
                                         s = 0; s < t.length; s++) {
                                    var n = t[s].type,
                                        r = t[s].duration,
                                        a = t[s].delay,
                                        o = -1 == t[s].count ? "infinite": t[s].count;
                                    i += (0 == s ? "animation:": ",") + n + " " + r + "s " + a + "s " + o + " " + (0 == s ? "both running": "forwards")
                                }
                                return i
                            }
                        },
                        setShapeHtml: function(e, i, s) {
                            if (this.scrollIndex == s) {
                                t(document).ready(function() {
                                    var s, n = t(".svg3" + i);
                                    if (1 != t(".svg3_" + i).attr("data-flag") && (t(".svg3_" + i).attr("data-flag", 1), e.src)) {
                                        n = t(".svg3_" + i); (s = document.createElementNS("http://www.w3.org/2000/svg", "svg")).setAttribute("class", "element svg-element"),
                                            t.ajax({
                                                type: "GET",
                                                url: e.src,
                                                dataType: "xml",
                                                async: !1,
                                                success: function(r) {
                                                    var a = r.getElementsByTagName("svg"),
                                                        o = parseFloat(void 0 == t(a).attr("width") ? "100%": t(a).attr("width")),
                                                        l = parseFloat(void 0 == t(a).attr("height") ? "100%": t(a).attr("height")),
                                                        d = e. in .properties ? e. in .properties: {};
                                                    if (e. in .properties.colors) {
                                                        var c = 0;
                                                        t(a).find("[fill], [style*='fill'], [stroke]").each(function(e) {
                                                            var i = "color" + c;
                                                            if (void 0 == d.colors[i]) {
                                                                var s = c - 1;
                                                                "none" != t(this).attr("fill") && void 0 != t(this).attr("fill") ? (t(this).attr("fill", d.colors["color" + s]), c += 1) : "none" != t(this).attr("stroke") && void 0 != t(this).attr("stroke") && (t(this).attr("stroke", d.colors["color" + s]), c += 1)
                                                            } else "none" != t(this).attr("fill") && void 0 != t(this).attr("fill") ? (t(this).attr("fill", d.colors[i]), c += 1) : "none" != t(this).attr("stroke") && void 0 != t(this).attr("stroke") && (t(this).attr("stroke", d.colors[i]), c += 1)
                                                        })
                                                    }
                                                    e. in .properties = d,
                                                        (s = a[0]).setAttribute("preserveAspectRatio", "none"),
                                                        s.setAttribute("width", "100%"),
                                                        s.setAttribute("height", "100%"),
                                                    void 0 == s.attributes.viewBox && s.setAttribute("viewBox", "0 0 " + parseInt(o) + " " + parseInt(l)),
                                                        s.setAttribute("style", "position:absolute"),
                                                        s.id = "svg3_" + i,
                                                    0 == n[0].childNodes.length && n.append(s)
                                                }
                                            })
                                    }
                                })
                            }
                        },
                        oldShape: function(e) {
                            if ("" == e.svgdom) return "";
                            var i = t(e.svgdom),
                                s = e. in .properties.colors;
                            if (void 0 !== s) var r = n()(s);
                            else r = [];
                            if (! (r.length > 0)) return t(i).attr({
                                width: e. in .css.width,
                                height: e. in .css.height,
                                preserveAspectRatio: "none meet"
                            }),
                                t(i[i.length - 1]).prop("outerHTML");
                            return i.find("[fill], [style*='fill']").each(function(e) {
                                void 0 == r[e] ? t(this).attr("fill", r[e - 1]) : t(this).attr("fill", r[e])
                            }),
                                t(i).attr({
                                    width: e. in .css.width,
                                    height: e. in .css.height,
                                    preserveAspectRatio: "none meet"
                                }),
                                t(i[i.length - 1]).prop("outerHTML")
                        },
                        newShape: function(e) {
                            var i = e. in .properties.colors,
                                s = e. in .properties.oldColors;
                            if (void 0 !== i) var r = n()(i),
                                a = n()(s);
                            else r = [];
                            var o = t(e.svgdom);
                            return t(o).find("*[fill]").each(function() {
                                for (var e = t(this).attr("fill").split(" "), i = 0; i < e.length; i++) for (var s = 0; s < a.length; s++) e[i] == a[s] && t(this).attr("fill", r[s])
                            }),
                                t(o).prop("outerHTML")
                        },
                        getUrl: function(t, e) {
                            if (t. in .properties.url.indexOf("http://") > -1 || t. in .properties.url.indexOf("https://") > -1) var i = t. in .properties.url;
                            else i = "http://" + t. in .properties.url.replace("http://", "");
                            return "javascript:window.parent.location.href='" + i + "'"
                        },
                        requestUrl: function(e, i, s, n) {
                            var r = this;
                            this.$http({
                                method: "post",
                                url: s,
                                data: e
                            }).then(function(i) {
                                1 == i.data.code ? n ? (t(".inputs").find("input").val(""), t(".inputs").find("input").nextAll("span").show(), r.$Message.success(i.data.msg)) : (t("#" + e.zanid).find("span").text(i.data.data.num), r.$Message.success(i.data.msg)) : r.$Message.error(i.data.msg)
                            }).
                            catch(function(t) {
                                r.$Message.error("网络开小差啦~")
                            })
                        },
                        teleClick: function(t, e) {
                            window.location.href = "tel://" + t. in .properties.tel
                        },
                        submitZan: function(t, e) {
                            var i = new URLSearchParams,
                                s = {
                                    oid: this.sceneInfoSub.id,
                                    pageid: e.id,
                                    zanid: t. in .properties.id,
                                    type: this.additional.type
                                };
                            this.requestUrl(s, i, o.a.submitZan(), !1)
                        },
                        getZanCount: function(e, i) {
                            var s = this;
                            t(document).ready(function() {
                                var n = t("#" + e. in .properties.id),
                                    r = o.a.getZanCount();
                                0 != n.attr("data-flag") && void 0 !== n.attr("data-flag") || s.$http({
                                    method: "post",
                                    url: r,
                                    data: {
                                        oid: s.sceneInfoSub.id,
                                        pageid: i.id,
                                        zanid: e. in .properties.id,
                                        type: s.additional.type
                                    }
                                }).then(function(t) {
                                    n.attr("data-flag", 1),
                                    t.data.data.num > 0 && n.find("span").text(t.data.data.num)
                                }).
                                catch(function(t) {})
                            })
                        },
                        submitData: function(e, i, s) {
                            var n, r = new URLSearchParams,
                                a = {};
                            r.oid = this.sceneInfoSub.id,
                                r.pageid = i.id;
                            for (var l = 0; l < e.length; l++) if ("i" == e[l].type) {
                                if (n = t(s.target).parents(".elementCanvas").find("#" + e[l].id).val(), 1 == e[l]. in .properties.require && ("" == n || "undefined" == n || void 0 == n)) return void this.$Message.success("请补全信息");
                                if ("" !== n && "undefined" !== n && void 0 !== n) {
                                    var d = e[l]. in .properties.placeholder;
                                    if ("姓名" == d) {
                                        if (n.length > 15) return void this.$Message.error("姓名过长")
                                    } else if ("手机" == d) {
                                        if (!/^1(3|4|5|7|8)\d{9}$/.test(n)) return void this.$Message.error("手机号有误")
                                    } else if ("邮箱" == d) {
                                        if (!/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/.test(n)) return void this.$Message.error("邮箱地址有误")
                                    } else if ("文本" == d && n.length > 150) return void this.$Message.error("文本过长")
                                }
                                a[e[l].id] = n,
                                "s" == e[l].type && (r.ok = e[l]. in .properties.ok)
                            } else if ("r" == e[l].type) {
                                var c = t('input:radio[name="' + e[l]. in .properties.str + '"]:checked').val();
                                if ("1" === e[l]. in .properties.require && ("" == c || "undefined" == c || void 0 == c)) return void this.$Message.success("请补全信息");
                                a[e[l].id] = c
                            } else if ("c" == e[l].type) {
                                var p = document.getElementsByName(e[l]. in .properties.str),
                                    u = [];
                                for (var h in p) p[h].checked && u.push(p[h].value);
                                if ("1" === e[l]. in .properties.require && 0 == u.length) return void this.$Message.success("请补全信息");
                                a[e[l].id] = u
                            } else if ("p" == e[l].type) {
                                c = t("#" + e[l].id + " option:selected").text();
                                if ("1" === e[l]. in .properties.require && "undefined" === c) return void this.$Message.success("请补全信息");
                                a[e[l].id] = c
                            } else if ("g" == e[l].type) {
                                c = e[l]. in .properties.value;
                                if ("1" === e[l]. in .properties.require && 0 === c) return void this.$Message.success("请补全信息");
                                a[e[l].id] = c
                            }
                            r.content = a;
                            var f = {
                                oid: i.sceneid,
                                pageid: i.id,
                                formid: i.id,
                                content: r.content,
                                type: this.additional.type
                            };
                            this.requestUrl(f, r, o.a.submitForm(), !0)
                        },
                        inputfocus: function(e) {
                            t(e.target).nextAll("span").hide()
                        },
                        inputblur: function(e) {
                            "" == t(e.target).val() ? t(e.target).nextAll("span").show() : t(e.target).nextAll("span").hide()
                        },
                        showimg: function(t, e) {
                            var i = 0;
                            /.(gif)$/.test(t) && (i = 1);
                            var s = 0;
                            return "100%" == e || void 0 === e ? s = 7 : (s = Math.ceil(e / 100)) > 9 && (s = 9),
                            -1 != t.indexOf("http://") && (t = t.replace("http://", "https://")),
                                1 == i ? s > 6 ? t + "?x-oss-process=image/resize,w_" + s + "00": t: t + "?x-oss-process=image/resize,w_" + s + "00"
                        },
                        jumpClick: function(t) {
                            if (1 == t.hasUrl) {
                                if (t.jumpUrl.indexOf("http://") > -1 || t.jumpUrl.indexOf("https://") > -1) var e = t.jumpUrl;
                                else e = "http://" + t.jumpUrl.replace("http://", "");
                                window.parent.location.href = e
                            } else 2 == t.hasUrl && (this.scrollIndex = t.jumpPageNumber - 1, this.pageStr = t.jumpPageNumber + "/" + this.pagesData.length, c.slideTo(this.scrollIndex))
                        }
                    }
                }
            }).call(e, i("7t+N"))
        },
        DV4W: function(t, e) {},
        EdQI: function(t, e, i) {
            "use strict";
            var s = {
                    data: function() {
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
                    },
                    props: {
                        item: {
                            type: Object,
                            default:
                                null
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
                            default:
                                "已结束"
                        },
                        type: {
                            type: String
                        }
                    },
                    watch: {
                        endTime: function(t) {
                            this.init()
                        }
                    },
                    mounted: function() {
                        this.init()
                    },
                    methods: {
                        setLineHeight: function(t) {
                            var e = t. in .css,
                                i = "";
                            for (var t in e) {
                                var s = e[t];
                                "height" === t ? (this.type, i += "lineHeight:" + .5 * (s - 20) + "px;") : "color" === t && (i += t + ":" + s + ";")
                            }
                            return i
                        },
                        setStyle: function(t) {
                            var e = t. in .css,
                                i = "";
                            for (var t in e) {
                                var s = e[t];
                                if ("width" === t) this.type,
                                    i += t + ":" + (s - 40) / 4 + "px;";
                                else if ("backgroundColor" === t) {
                                    if ("" == t) {
                                        i += t + ":rgb(0,0,0)";
                                        continue
                                    }
                                    i += t + ":" + s + ";"
                                }
                            }
                            return i
                        },
                        init: function() {
                            var t = this;
                            10 == this.startTime.toString().length ? this.star = 1e3 * this.startTime: this.star = this.startTime,
                                10 == this.endTime.toString().length ? this.end = 1e3 * this.endTime: this.end = this.endTime,
                                this.currentTime ? 10 == this.currentTime.toString().length ? this.current = 1e3 * this.currentTime: this.current = this.currentTime: this.current = (new Date).getTime(),
                            this.end < this.current || (this.current < this.star ? (this.$set(this, "tipShow", !0), setInterval(function() {
                                    t.runTime(t.star, t.current, t.start_message, !1)
                                },
                                1e3)) : (this.end > this.current && this.star < this.current || this.star == this.current) && (this.$set(this, "tipShow", !1), this.msTime.show = !0, setInterval(function() {
                                    t.runTime(t.end, (new Date).getTime(), t.end_message, !0)
                                },
                                1e3)))
                        },
                        setInStyle: function(t) {
                            var e = t. in .css,
                                i = "",
                                s = !1;
                            for (var t in e) {
                                var n = e[t];
                                if ("width" === t) {
                                    if (0 == isNaN(n)) {
                                        i += t + ":" + n + "px;";
                                        continue
                                    }
                                    i += t + ":" + n + ";"
                                } else if ("height" === t) {
                                    if (0 == isNaN(n)) {
                                        i += t + ":" + n + "px;";
                                        continue
                                    }
                                    i += t + ":" + n + "px;"
                                } else if ("top" === t) {
                                    if (0 == isNaN(n)) {
                                        i += t + ":" + n + "px;";
                                        continue
                                    }
                                    i += t + ":" + n + ";"
                                } else if ("left" === t) {
                                    if (0 == isNaN(n)) {
                                        i += t + ":" + n + "px;";
                                        continue
                                    }
                                    i += t + ":" + n + ";"
                                } else if ("backgroundColor" === t) {
                                    if ("" == t) {
                                        i += t + ":rgb(0,0,0)";
                                        continue
                                    }
                                    i += t + ":" + n + ";"
                                } else if ("borderRadius" === t) i += t + ":" + (n = "string" == typeof n ? parseInt(n) : n) + "px;";
                                else if ("word-wrap" == t) s = !0;
                                else if ("fontSize" == t) i += t + ":" + (n = "string" == typeof n ? parseInt(n) : n) + "px;";
                                else {
                                    if ("backgroundImage" == t) continue;
                                    if ("backgroundSize" == t) continue;
                                    if ("backgroundPosition" == t) continue;
                                    if ("borderWidth" == t) continue;
                                    if ("borderStyle" == t) continue;
                                    if ("borderColor" == t) continue;
                                    if ("lineHeight" == t) continue;
                                    i += t + ":" + n + ";"
                                }
                            }
                            return s || (i += "word-wrap:break-word"),
                                i
                        },
                        runTime: function(t, e, i, s) {
                            var n = this.msTime,
                                r = t - e;
                            r > 0 ? (this.msTime.show = !0, n.day = Math.floor(r / 864e5), r -= 864e5 * n.day, n.hour = Math.floor(r / 36e5), r -= 36e5 * n.hour, n.minutes = Math.floor(r / 6e4), r -= 6e4 * n.minutes, n.seconds = Math.floor(r / 1e3).toFixed(0), r -= 1e3 * n.seconds, n.hour < 10 && (n.hour = "0" + n.hour), n.minutes < 10 && (n.minutes = "0" + n.minutes), n.seconds < 10 && (n.seconds = "0" + n.seconds)) : i()
                        },
                        start_message: function() {
                            var t = this;
                            this.$set(this, "tipShow", !1),
                                this.$emit("start_callback", this.msTime.show),
                                setTimeout(function() {
                                        t.runTime(t.end, t.star, t.end_message, !0)
                                    },
                                    1)
                        },
                        end_message: function() {
                            this.msTime.show = !1,
                                this.$emit("end_callback", this.msTime.show)
                        }
                    }
                },
                n = {
                    render: function() {
                        var t = this,
                            e = t.$createElement,
                            i = t._self._c || e;
                        return i("div", [t.msTime.show ? i("div", {
                                staticClass: "countdownbox"
                            },
                            [i("div", {
                                    staticClass: "timepart",
                                    style: t.setStyle(t.item)
                                },
                                [i("h1", {
                                        style: t.setLineHeight(t.item)
                                    },
                                    [t._v(t._s(t.msTime.day))]), t._v(" "), i("p", {
                                        style: t.setLineHeight(t.item)
                                    },
                                    [t._v("天")])]), t._v(" "), i("div", {
                                    staticClass: "timepart",
                                    style: t.setStyle(t.item)
                                },
                                [i("h1", {
                                        style: t.setLineHeight(t.item)
                                    },
                                    [t._v(t._s(t.msTime.hour))]), t._v(" "), i("p", {
                                        style: t.setLineHeight(t.item)
                                    },
                                    [t._v("时")])]), t._v(" "), i("div", {
                                    staticClass: "timepart",
                                    style: t.setStyle(t.item)
                                },
                                [i("h1", {
                                        style: t.setLineHeight(t.item)
                                    },
                                    [t._v(t._s(t.msTime.minutes))]), t._v(" "), i("p", {
                                        style: t.setLineHeight(t.item)
                                    },
                                    [t._v("分")])]), t._v(" "), i("div", {
                                    staticClass: "timepart",
                                    style: t.setStyle(t.item)
                                },
                                [i("h1", {
                                        style: t.setLineHeight(t.item)
                                    },
                                    [t._v(t._s(t.msTime.seconds))]), t._v(" "), i("p", {
                                        style: t.setLineHeight(t.item)
                                    },
                                    [t._v("秒")])])]) : t._e(), t._v(" "), t.msTime.show ? t._e() : i("div", {
                                staticClass: "countdownwrap"
                            },
                            [i("span", {
                                    staticClass: "text",
                                    staticStyle: {
                                        display: "block"
                                    },
                                    style: t.setInStyle(t.item)
                                },
                                [i("span", {
                                        staticClass: "num"
                                    },
                                    [t._v(t._s(t.endText))])])])])
                    },
                    staticRenderFns: []
                };
            var r = i("VU/8")(s, n, !1,
                function(t) {
                    i("jqc1")
                },
                "data-v-186933bd", null);
            e.a = r.exports
        },
        "N+zL": function(t, e, i) {
            "use strict";
            Object.defineProperty(e, "__esModule", {
                value: !0
            });
            var s = {
                    render: function() {
                        var t = this.$createElement;
                        return (this._self._c || t)("div", {
                                class: this.slideClass
                            },
                            [this._t("default")], 2)
                    },
                    staticRenderFns: []
                },
                n = i("VU/8")({
                        name: "swiper-slide",
                        data: function() {
                            return {
                                slideClass: "swiper-slide"
                            }
                        },
                        ready: function() {
                            this.update()
                        },
                        mounted: function() {
                            this.update(),
                            this.$parent.options.slideClass && (this.slideClass = this.$parent.options.slideClass)
                        },
                        updated: function() {
                            this.update()
                        },
                        attached: function() {
                            this.update()
                        },
                        methods: {
                            update: function() {
                                this.$parent && this.$parent.swiper && this.$parent.swiper.update && (this.$parent.swiper.update(!0), this.$parent.options.loop && this.$parent.swiper.reLoop())
                            }
                        }
                    },
                    s, !1, null, null, null);
            e.
                default = n.exports
        },
        NHnr: function(t, e, i) {
            "use strict";
            Object.defineProperty(e, "__esModule", {
                value: !0
            });
            var s = {};
            i.d(s, "initOriginData",
                function() {
                    return b
                }),
                i.d(s, "selectPage",
                    function() {
                        return S
                    });
            var n = {};
            i.d(n, "originData",
                function() {
                    return _
                }),
                i.d(n, "pagesData",
                    function() {
                        return C
                    }),
                i.d(n, "currentPageBg",
                    function() {
                        return k
                    }),
                i.d(n, "currentPage",
                    function() {
                        return I
                    }),
                i.d(n, "sceneInfo",
                    function() {
                        return z
                    }),
                i.d(n, "sceneInfoSub",
                    function() {
                        return T
                    }),
                i.d(n, "additional",
                    function() {
                        return W
                    });
            var r = i("7+uW"),
                a = i("NYxO"),
                o = i("Dd8w"),
                l = i.n(o),
                d = i("gyMJ"),
                c = i("14xJ"),
                p = {
                    render: function() {
                        var t = this,
                            e = t.$createElement,
                            i = t._self._c || e;
                        return i("div", {
                                staticClass: "preview"
                            },
                            [i("div", {
                                    staticClass: "previewtk"
                                },
                                [i("audio", {
                                    attrs: {
                                        id: "audio2",
                                        loop: "loop",
                                        src: t.sceneInfoSub.music,
                                        autoplay: "autoplay"
                                    }
                                }), t._v(" "), i("div", {
                                        staticClass: "workwrap"
                                    },
                                    [i("div", {
                                            staticClass: "workcont"
                                        },
                                        [i("swiper", {
                                                style: "height:" + t.height + "px;",
                                                attrs: {
                                                    options: t.swiperOption,
                                                    id: "my"
                                                }
                                            },
                                            t._l(t.pagesData,
                                                function(e, s) {
                                                    return i("swiper-slide", {
                                                            key: s,
                                                            staticStyle: {
                                                                overflow: "hidden"
                                                            }
                                                        },
                                                        [i("div", {
                                                                directives: [{
                                                                    name: "show",
                                                                    rawName: "v-show",
                                                                    value: t.scrollIndex == s,
                                                                    expression: "scrollIndex == index1"
                                                                }],
                                                                staticClass: "bgCanvas",
                                                                class: t.isHide(s),
                                                                staticStyle: {
                                                                    dispaly: "none"
                                                                },
                                                                style: t.setBgStyle(e)
                                                            },
                                                            [i("div", {
                                                                    staticClass: "elementCanvas",
                                                                    staticStyle: {
                                                                        "transform-origin": "0 0",
                                                                        transform: "scale(0.5)"
                                                                    },
                                                                    style: t.setTop()
                                                                },
                                                                t._l(e.elements,
                                                                    function(n, r) {
                                                                        return i("div", {
                                                                                key: r,
                                                                                class: ["item_wrapper", n.class],
                                                                                staticStyle: {
                                                                                    position: "absolute"
                                                                                },
                                                                                style: t.setOutStyle(n, n.out.css, n.type),
                                                                                attrs: {
                                                                                    type: n.type,
                                                                                    index: r
                                                                                }
                                                                            },
                                                                            [i("div", {
                                                                                    staticClass: "content",
                                                                                    style: t.setNewAni(n.anim, s)
                                                                                },
                                                                                ["4" == n.type ? i("div", {
                                                                                        staticClass: "img-element"
                                                                                    },
                                                                                    [i("div", {
                                                                                            staticClass: "wrapper",
                                                                                            style: t.setOutImageStyle(n, n.out.css, n.type)
                                                                                        },
                                                                                        [i("img", {
                                                                                            staticClass: "img-render render",
                                                                                            staticStyle: {
                                                                                                opacity: "0"
                                                                                            },
                                                                                            style: t.setRenderStyle(n),
                                                                                            attrs: {
                                                                                                src: t.showimg(n.src, n. in .css.width)
                                                                                            },
                                                                                            on: {
                                                                                                click: function(e) {
                                                                                                    t.jumpClick(n. in .properties)
                                                                                                }
                                                                                            }
                                                                                        })])]) : t._e(), t._v(" "), "2" == n.type ? i("div", {
                                                                                        staticClass: "text-element",
                                                                                        attrs: {
                                                                                            id: r
                                                                                        }
                                                                                    },
                                                                                    [i("div", {
                                                                                            staticClass: "wrapper",
                                                                                            style: t.setInStyle(n, n.type)
                                                                                        },
                                                                                        [i("div", {
                                                                                                staticClass: "text-render render"
                                                                                            },
                                                                                            [i("div", {
                                                                                                    staticClass: "scaleArea",
                                                                                                    staticStyle: {
                                                                                                        height: "100%"
                                                                                                    }
                                                                                                },
                                                                                                [i("div", {
                                                                                                        staticClass: "vue-edit-area",
                                                                                                        style: t.setTextStyle(n),
                                                                                                        attrs: {
                                                                                                            danyeid: r
                                                                                                        },
                                                                                                        domProps: {
                                                                                                            innerHTML: t._s(n.content)
                                                                                                        },
                                                                                                        on: {
                                                                                                            click: function(e) {
                                                                                                                t.jumpClick(n. in .properties)
                                                                                                            }
                                                                                                        }
                                                                                                    },
                                                                                                    [i("i")])])])])]) : t._e(), t._v(" "), "m" == n.type ? i("div", {
                                                                                        staticClass: "map-element"
                                                                                    },
                                                                                    [i("div", {
                                                                                            staticClass: "wrapper",
                                                                                            style: t.setInStyle(n, n.type)
                                                                                        },
                                                                                        [i("div", {
                                                                                                staticClass: "map-render render",
                                                                                                style: t.setRenderStyle(n)
                                                                                            },
                                                                                            [i("img", {
                                                                                                staticStyle: {
                                                                                                    width: "100%",
                                                                                                    height: "100%"
                                                                                                },
                                                                                                attrs: {
                                                                                                    src: "http://api.map.baidu.com/staticimage/v2?ak=WtfAdHwd1tMOCf2dzdRIhNZkSq8V7o5W&width=600&height=300&dpiType=ph&markers=" + n. in .properties.address + "|" + n. in .properties.lng + "," + n. in .properties.lat + "&markerStyles=l,,0xff0000&center=" + n. in .properties.address + "&labels=" + n. in .properties.address + "|" + n. in .properties.lng + "," + n. in .properties.lat + "&zoom=17&labelStyles=我在这,1,28,0xffffff,0x1abd9b,1"
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
                                                                                                        click: function(e) {
                                                                                                            t.goLocation(n. in .properties)
                                                                                                        }
                                                                                                    }
                                                                                                },
                                                                                                [t._v("导航")])])])]) : t._e(), t._v(" "), "phoneCallButton" == n.type ? i("div", [i("div", {
                                                                                        staticClass: "wrapper",
                                                                                        style: t.setInStyle(n, r)
                                                                                    },
                                                                                    [i("div", {
                                                                                            staticClass: "link-button-render render",
                                                                                            style: t.setRenderStyle(n)
                                                                                        },
                                                                                        [i("div", {
                                                                                                staticClass: "text1 link-button-render-text"
                                                                                            },
                                                                                            [i("a", {
                                                                                                    staticClass: "telebtn",
                                                                                                    style: t.setInStyle(n, n.type),
                                                                                                    on: {
                                                                                                        click: function(i) {
                                                                                                            t.teleClick(n, e)
                                                                                                        }
                                                                                                    }
                                                                                                },
                                                                                                [i("i", {
                                                                                                    staticClass: "iconfont icon-bohao",
                                                                                                    style: t.setBtnStyle(n. in .css)
                                                                                                }), t._v(" " + t._s(n.content))])]), t._v(" "), n.isImg ? i("img", {
                                                                                            attrs: {
                                                                                                src: n.pic.url
                                                                                            }
                                                                                        }) : t._e()])])]) : t._e(), t._v(" "), "interActionButton" == n.type ? i("div", [i("div", {
                                                                                        staticClass: "wrapper",
                                                                                        style: t.setInStyle(n, r)
                                                                                    },
                                                                                    [i("div", {
                                                                                            staticClass: "link-button-render render",
                                                                                            style: t.setRenderStyle(n)
                                                                                        },
                                                                                        [i("div", {
                                                                                                staticClass: "text1 link-button-render-text"
                                                                                            },
                                                                                            [i("a", {
                                                                                                    staticClass: "zanbtn",
                                                                                                    class: t.getZanCount(n, e),
                                                                                                    style: t.setInStyle(n, n.type),
                                                                                                    attrs: {
                                                                                                        id: n. in .properties.id,
                                                                                                        "data-flag": "0"
                                                                                                    },
                                                                                                    on: {
                                                                                                        click: function(i) {
                                                                                                            t.submitZan(n, e)
                                                                                                        }
                                                                                                    }
                                                                                                },
                                                                                                [i("i", {
                                                                                                    staticClass: "iconfont icon-dianzan1",
                                                                                                    style: t.setBtnStyle(n. in .css)
                                                                                                }), t._v("  "), i("span", [t._v(t._s(n.content))])])]), t._v(" "), n.isImg ? i("img", {
                                                                                            attrs: {
                                                                                                src: n.pic.url
                                                                                            }
                                                                                        }) : t._e()])])]) : t._e(), t._v(" "), "linkButton" == n.type ? i("div", [i("div", {
                                                                                        staticClass: "wrapper",
                                                                                        style: t.setInStyle(n, r)
                                                                                    },
                                                                                    [i("div", {
                                                                                            staticClass: "link-button-render render",
                                                                                            style: t.setRenderStyle(n)
                                                                                        },
                                                                                        [i("div", {
                                                                                                staticClass: "text1 link-button-render-text"
                                                                                            },
                                                                                            [i("a", {
                                                                                                    staticClass: "linkbtn",
                                                                                                    style: t.setInStyle(n, n.type),
                                                                                                    attrs: {
                                                                                                        href: t.getUrl(n, e)
                                                                                                    }
                                                                                                },
                                                                                                [t._v(t._s(n.content))])]), t._v(" "), n.isImg ? i("img", {
                                                                                            attrs: {
                                                                                                src: n.pic.url
                                                                                            }
                                                                                        }) : t._e()])])]) : t._e(), t._v(" "), "countDown" == n.type ? i("div", [i("div", {
                                                                                        staticClass: "wrapper",
                                                                                        style: t.setInStyle(n, r)
                                                                                    },
                                                                                    [i("div", {
                                                                                            staticClass: "link-button-render render",
                                                                                            style: t.setRenderStyle(n)
                                                                                        },
                                                                                        [i("countdownbox", {
                                                                                            attrs: {
                                                                                                startTime: (new Date).getTime(),
                                                                                                endText: n. in .properties.endtip,
                                                                                                endTime: parseInt(n. in .properties.deadlineTime),
                                                                                                item: n,
                                                                                                type: "workarea"
                                                                                            }
                                                                                        }), t._v(" "), n.isImg ? i("img", {
                                                                                            attrs: {
                                                                                                src: n.pic.url
                                                                                            }
                                                                                        }) : t._e()], 1)])]) : t._e(), t._v(" "), "h" == n.type ? i("div", {
                                                                                        staticClass: "shape-element"
                                                                                    },
                                                                                    [i("div", {
                                                                                            staticClass: "wrapper",
                                                                                            style: t.setInStyle(n, r)
                                                                                        },
                                                                                        [i("div", {
                                                                                                staticClass: "shape-render render",
                                                                                                style: t.setRenderStyle(n)
                                                                                            },
                                                                                            [i("div", {
                                                                                                staticClass: "svg",
                                                                                                class: "svg3_" + r + "_" + s,
                                                                                                attrs: {
                                                                                                    "data-flag": "0"
                                                                                                },
                                                                                                domProps: {
                                                                                                    innerHTML: t._s(t.setShapeHtml(n, r + "_" + s, s))
                                                                                                }
                                                                                            })])])]) : t._e(), t._v(" "), "i" == n.type ? i("div", {
                                                                                        staticClass: "shape-element"
                                                                                    },
                                                                                    [i("div", {
                                                                                            staticClass: "wrapper",
                                                                                            style: t.setInStyle(n, r)
                                                                                        },
                                                                                        [i("div", {
                                                                                                staticClass: "new-form-render render"
                                                                                            },
                                                                                            ["i" == n.type ? i("div", {
                                                                                                    staticClass: "forminputbox",
                                                                                                    style: t.setFormTextSize(n)
                                                                                                },
                                                                                                [i("input", {
                                                                                                    staticStyle: {
                                                                                                        display: "block",
                                                                                                        position: "absolute",
                                                                                                        margin: "0",
                                                                                                        padding: "0",
                                                                                                        border: "0"
                                                                                                    },
                                                                                                    style: t.setTextStyle1(n),
                                                                                                    attrs: {
                                                                                                        type: "text",
                                                                                                        id: n.id
                                                                                                    },
                                                                                                    on: {
                                                                                                        focus: t.inputfocus,
                                                                                                        blur: t.inputblur
                                                                                                    }
                                                                                                }), t._v(" "), i("span", {
                                                                                                        staticStyle: {
                                                                                                            position: "relative",
                                                                                                            "pointer-events": "none",
                                                                                                            opacity: "0.7",
                                                                                                            "padding-left": "10px"
                                                                                                        },
                                                                                                        style: t.setSpanStyle(n. in .css)
                                                                                                    },
                                                                                                    [t._v(t._s(n. in .properties.placeholder))]), t._v(" "), "1" == n. in .properties.require ? i("span", {
                                                                                                        staticStyle: {
                                                                                                            position: "relative",
                                                                                                            "pointer-events": "none",
                                                                                                            color: "red"
                                                                                                        },
                                                                                                        style: t.setSpanStyle(n. in .css)
                                                                                                    },
                                                                                                    [t._v("*")]) : t._e()]) : t._e()])])]) : t._e(), t._v(" "), "s" == n.type ? i("div", {
                                                                                        staticClass: "shape-element"
                                                                                    },
                                                                                    [i("div", {
                                                                                            staticClass: "wrapper",
                                                                                            style: t.setInStyle(n, r)
                                                                                        },
                                                                                        [i("div", {
                                                                                                staticClass: "shape-render render"
                                                                                            },
                                                                                            ["s" == n.type ? i("div", {
                                                                                                    style: t.setOutStyle(n, n. in .css, n.type),
                                                                                                    on: {
                                                                                                        click: function(i) {
                                                                                                            t.submitData(e.elements, e, i)
                                                                                                        }
                                                                                                    }
                                                                                                },
                                                                                                [t._v("\n                            " + t._s(n. in .properties.str) + "\n                          ")]) : t._e()])])]) : t._e(), t._v(" "), "r" == n.type && 1 != n.is_del ? i("div", {
                                                                                        staticClass: "shape-element"
                                                                                    },
                                                                                    [i("div", {
                                                                                            staticClass: "wrapper"
                                                                                        },
                                                                                        [i("div", {
                                                                                                staticClass: "shape-render render"
                                                                                            },
                                                                                            ["r" == n.type ? i("div", {
                                                                                                    staticClass: "radiogroup",
                                                                                                    staticStyle: {
                                                                                                        "border-radius": "6px"
                                                                                                    }
                                                                                                },
                                                                                                [i("p", {
                                                                                                        style: t.setRadioTitle(n, r)
                                                                                                    },
                                                                                                    [t._v(t._s(n. in .properties.str))]), t._v(" "), i("div", {
                                                                                                        staticClass: "groupwrap"
                                                                                                    },
                                                                                                    [i("ul", {
                                                                                                            staticStyle: {
                                                                                                                background: "#fff"
                                                                                                            },
                                                                                                            style: t.setRadioStyle(n, r),
                                                                                                            attrs: {
                                                                                                                id: n.id
                                                                                                            }
                                                                                                        },
                                                                                                        t._l(n. in .properties.value,
                                                                                                            function(e, s) {
                                                                                                                return i("li", {
                                                                                                                        key: s
                                                                                                                    },
                                                                                                                    [i("input", {
                                                                                                                        attrs: {
                                                                                                                            type: "radio",
                                                                                                                            name: n. in .properties.str
                                                                                                                        },
                                                                                                                        domProps: {
                                                                                                                            value: e
                                                                                                                        }
                                                                                                                    }), i("span", [t._v(t._s(e))])])
                                                                                                            }))])]) : t._e()])])]) : t._e(), t._v(" "), "c" == n.type && 1 != n.is_del ? i("div", {
                                                                                        staticClass: "shape-element"
                                                                                    },
                                                                                    [i("div", {
                                                                                            staticClass: "wrapper"
                                                                                        },
                                                                                        [i("div", {
                                                                                                staticClass: "shape-render render"
                                                                                            },
                                                                                            ["c" == n.type ? i("div", {
                                                                                                    staticClass: "radiogroup",
                                                                                                    staticStyle: {
                                                                                                        "border-radius": "6px"
                                                                                                    }
                                                                                                },
                                                                                                [i("p", {
                                                                                                        style: t.setRadioTitle(n, r)
                                                                                                    },
                                                                                                    [t._v(t._s(n. in .properties.str))]), t._v(" "), i("div", {
                                                                                                        staticClass: "groupwrap"
                                                                                                    },
                                                                                                    [i("ul", {
                                                                                                            staticStyle: {
                                                                                                                background: "#fff"
                                                                                                            },
                                                                                                            style: t.setRadioStyle(n, r),
                                                                                                            attrs: {
                                                                                                                id: n.id
                                                                                                            }
                                                                                                        },
                                                                                                        t._l(n. in .properties.value,
                                                                                                            function(e, s) {
                                                                                                                return i("li", {
                                                                                                                        key: s
                                                                                                                    },
                                                                                                                    [i("input", {
                                                                                                                        attrs: {
                                                                                                                            type: "checkbox",
                                                                                                                            name: n. in .properties.str
                                                                                                                        },
                                                                                                                        domProps: {
                                                                                                                            value: e
                                                                                                                        }
                                                                                                                    }), i("span", [t._v(t._s(e))])])
                                                                                                            }))])]) : t._e()])])]) : t._e(), t._v(" "), "p" == n.type && 1 != n.is_del ? i("div", {
                                                                                        staticClass: "shape-element"
                                                                                    },
                                                                                    [i("div", {
                                                                                            staticClass: "wrapper"
                                                                                        },
                                                                                        [i("div", {
                                                                                                staticClass: "shape-render render"
                                                                                            },
                                                                                            ["p" == n.type ? i("div", {
                                                                                                    staticClass: "selectgroup"
                                                                                                },
                                                                                                [i("select", {
                                                                                                        staticStyle: {
                                                                                                            padding: "6px 10px"
                                                                                                        },
                                                                                                        style: t.setRadioStyle(n, r),
                                                                                                        attrs: {
                                                                                                            id: n.id
                                                                                                        }
                                                                                                    },
                                                                                                    [i("option", {
                                                                                                            style: t.setOptionStyle(n, r),
                                                                                                            attrs: {
                                                                                                                value: ""
                                                                                                            }
                                                                                                        },
                                                                                                        [t._v(t._s(n. in .properties.str))]), t._v(" "), t._l(n. in .properties.value,
                                                                                                        function(e, s) {
                                                                                                            return i("option", {
                                                                                                                    key: s,
                                                                                                                    style: t.setOptionStyle(n, r)
                                                                                                                },
                                                                                                                [t._v(t._s(e))])
                                                                                                        })], 2)]) : t._e()])])]) : t._e(), t._v(" "), "g" == n.type && 1 != n.is_del ? i("div", {
                                                                                        staticClass: "shape-element"
                                                                                    },
                                                                                    [i("div", {
                                                                                            staticClass: "wrapper"
                                                                                        },
                                                                                        [i("div", {
                                                                                                staticClass: "shape-render render"
                                                                                            },
                                                                                            [i("div", {
                                                                                                    staticClass: "ratinggroup",
                                                                                                    staticStyle: {
                                                                                                        "border-radius": "8px"
                                                                                                    },
                                                                                                    style: t.setRadioStyle(n, r)
                                                                                                },
                                                                                                [i("p", [t._v(t._s(n. in .properties.str))]), t._v(" "), i("div", {
                                                                                                        staticClass: "staricon fl"
                                                                                                    },
                                                                                                    [i("Rate", {
                                                                                                        staticStyle: {
                                                                                                            "font-size": "inherit"
                                                                                                        },
                                                                                                        model: {
                                                                                                            value: n. in .properties.value,
                                                                                                            callback: function(e) {
                                                                                                                t.$set(n. in .properties, "value", e)
                                                                                                            },
                                                                                                            expression: "item.in.properties.value"
                                                                                                        }
                                                                                                    })], 1)])])])]) : t._e()])])
                                                                    }))])])
                                                })), t._v(" "), t.is_page ? i("div", {
                                                staticClass: "pagecount",
                                                style: "bottom: " + (15 + t.top) + "px;"
                                            },
                                            [t._v(t._s(t.pageStr))]) : t._e(), t._v(" "), i("a", {
                                            staticClass: "musicbtn",
                                            class: {
                                                play: t.isPlay
                                            },
                                            style: "width:" + 30 * this.scaleWidth + "px;height:" + 30 * this.scaleHeight + "px;\n                top:" + 20 * this.scaleHeight + "px;right:" + 20 * this.scaleWidth + "px;",
                                            on: {
                                                click: t.playOrPauseMusic
                                            }
                                        }), t._v(" "), i("b")], 1)])])])
                    },
                    staticRenderFns: []
                };
            var u = function(t) {
                    i("lG8U")
                },
                h = {
                    name: "index",
                    data: function() {
                        return {
                            showLoading: !0
                        }
                    },
                    components: {
                        MainPage: i("VU/8")(c.a, p, !1, u, "data-v-3b09fec6", null).exports
                    },
                    computed: l()({},
                        Object(a.b)(["sceneInfoSub"])),
                    methods: {
                        initOriginData: function() {
                            // var t = this;
                            // var i = t;
                            // var initJsonData = JSON.parse("{\"code\":1,\"msg\":\"H5预览\",\"data\":{\"lists\":[{\"id\":27631,\"sceneid\":979,\"scenecode\":\"CYmE5b5PX6va\",\"pagenum\":1,\"create_time\":\"2017-07-24 14:11:56\",\"pagename\":\"新建页面\",\"userid\":0,\"thumb\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/bg\\/CYmE5b5PX6va_page0.jpg\",\"pagebg\":{\"backgroundColor\":\"#ffffff\",\"backgroundImage\":\"\"},\"update_time\":\"2018-01-29 15:51:09\",\"is_tpl\":0,\"pageanim\":{\"type\":\"\",\"interval\":0,\"duration\":0,\"autoplay\":\"\"},\"elements\":[{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/9055fb17f9f386b0f9220124c202d952.png\",\"out\":{\"css\":{\"width\":\"620\",\"height\":\"617\",\"top\":\"-214\",\"left\":\"20\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"620\",\"height\":\"617\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateIn\",\"duration\":\"1.5\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/85189dafcf31c96069816c4a896cccfd.png\",\"out\":{\"css\":{\"width\":\"620\",\"height\":\"617\",\"top\":\"776\",\"left\":\"12\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"620\",\"height\":\"617\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInUp\",\"duration\":\"1.5\",\"delay\":\"5\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/132da05f133b41a2ae47778c136954b0.png\",\"out\":{\"css\":{\"width\":\"478\",\"height\":\"35\",\"top\":\"660\",\"left\":\"84\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"478\",\"height\":\"35\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInNormal\",\"duration\":\"2\",\"delay\":\"4.5\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/b707b710b00635183345fe30e45d6205.png\",\"out\":{\"css\":{\"width\":\"99\",\"height\":\"188\",\"top\":\"459\",\"left\":\"83\",\"opacity\":\"0.6\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"99\",\"height\":\"188\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateInDownRight\",\"duration\":\"1.2\",\"delay\":\"4\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/603cbbadf8618c7f3c3455e3f35d112e.png\",\"out\":{\"css\":{\"width\":\"234\",\"height\":\"85\",\"top\":\"561\",\"left\":\"180\",\"opacity\":\"0.6\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"234\",\"height\":\"85\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInDown\",\"duration\":\"1.5\",\"delay\":\"2.5\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/2f479ca8b5d515464924df746911a596.png\",\"out\":{\"css\":{\"width\":\"144\",\"height\":\"46\",\"top\":\"601\",\"left\":\"414\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"144\",\"height\":\"46\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInDown\",\"duration\":\"1.5\",\"delay\":\"3\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"Invitation\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"614\",\"height\":\"169\",\"top\":\"397\",\"left\":\"35\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"46\",\"difX\":\"11\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"614\",\"height\":\"169\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#102a40\",\"fontSize\":\"110px\",\"lineHeight\":\"1.8\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"fadeInDown\",\"duration\":\"1.5\",\"delay\":\"2\",\"count\":\"1\"}]}]},{\"id\":27632,\"sceneid\":979,\"scenecode\":\"CYmE5b5PX6va\",\"pagenum\":2,\"create_time\":\"2017-07-24 14:11:58\",\"pagename\":\"新建页面\",\"userid\":0,\"thumb\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/bg\\/CYmE5b5PX6va_page1.jpg\",\"pagebg\":{\"backgroundColor\":\"#ffffff\",\"backgroundImage\":\"\"},\"update_time\":\"2018-01-29 15:51:09\",\"is_tpl\":0,\"pageanim\":{\"type\":\"\",\"interval\":0,\"duration\":0,\"autoplay\":\"\"},\"elements\":[{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/fb809c8854855cf2bdedba31553f1b68.png\",\"out\":{\"css\":{\"width\":\"640\",\"height\":\"1008\",\"top\":\"1\",\"left\":\"0\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"640\",\"height\":\"1008\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInLeft\",\"duration\":\"1\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/978e28f9ebec48271bdec2338c086505.png\",\"out\":{\"css\":{\"width\":\"539\",\"height\":\"825\",\"top\":\"76\",\"left\":\"53\",\"opacity\":\"0.41\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"539\",\"height\":\"825\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\",\"fontFamily\":\"\",\"fontSize\":\"\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"fadeInRight\",\"duration\":\"1\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"邀请词\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"317\",\"height\":\"64\",\"top\":\"130\",\"left\":\"160\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"317\",\"height\":\"64\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#1abd9b\",\"fontSize\":\"40px\",\"lineHeight\":\"1.2\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInDown\",\"duration\":\"1\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"亲爱的yaoyue人\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"269\",\"height\":\"64\",\"top\":\"232\",\"left\":\"80\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"269\",\"height\":\"64\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#ffffff\",\"fontSize\":\"34px\",\"lineHeight\":\"1.2\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"fadeInDown\",\"duration\":\"1\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"人生在世，难免奔波，自初次相逢至今，算来已有几载春秋。多日不见，甚是挂念，适逢贱恭之际，相邀故友一聚，已备家常小菜，市井浊酒，扫榻以待。唯盼君携两袖清风，如约而至。\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"488\",\"height\":\"459\",\"top\":\"317\",\"left\":\"80\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"39\",\"difX\":\"6\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"488\",\"height\":\"459\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"left\",\"textValign\":\"middle\",\"color\":\"#ffffff\",\"fontSize\":\"34px\",\"lineHeight\":\"1.35\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInDown\",\"duration\":\"1\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"yaoyue团队\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"202\",\"height\":\"64\",\"top\":\"707\",\"left\":\"340\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"-15\",\"difX\":\"67\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"202\",\"height\":\"64\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"right\",\"textValign\":\"middle\",\"color\":\"#ffffff\",\"fontSize\":\"34px\",\"lineHeight\":\"1.2\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInDown\",\"duration\":\"1\",\"delay\":\"0.6\",\"count\":\"1\"}]}]},{\"id\":27633,\"sceneid\":979,\"scenecode\":\"CYmE5b5PX6va\",\"pagenum\":3,\"create_time\":\"2017-07-24 14:11:59\",\"pagename\":\"新建页面\",\"userid\":0,\"thumb\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/bg\\/CYmE5b5PX6va_page2.jpg\",\"pagebg\":{\"backgroundColor\":\"#ffffff\",\"backgroundImage\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/bg\\/10cc8464b43786066f656d5d975a46ec.jpg\"},\"update_time\":\"2018-01-29 15:51:09\",\"is_tpl\":0,\"pageanim\":{\"type\":\"\",\"interval\":0,\"duration\":0,\"autoplay\":\"\"},\"elements\":[{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/8208d8ed32524c21e2dc622b8d1fd058.png\",\"out\":{\"css\":{\"width\":\"183\",\"height\":\"184\",\"top\":\"149\",\"left\":\"25\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"183\",\"height\":\"184\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateInDownLeft\",\"duration\":\"1.5\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/0fa9c27ab23734aef23326f836cf555d.png\",\"out\":{\"css\":{\"width\":\"147\",\"height\":\"148\",\"top\":\"176\",\"left\":\"53\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"-1\",\"difX\":\"0\",\"margintop\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"147\",\"height\":\"148\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateInDownRight\",\"duration\":\"1.5\",\"delay\":\"1.2\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/bcee1660fc94ffdd27a31beb616a3b72.png\",\"out\":{\"css\":{\"width\":\"200\",\"height\":\"39\",\"top\":\"162\",\"left\":\"242\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"-4\",\"difX\":\"2\",\"margintop\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"200\",\"height\":\"39\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\",\"fontFamily\":\"\",\"fontSize\":\"\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInLeft\",\"duration\":\"1\",\"delay\":\"2\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/e0b96c677593413b8e50e7caaf864f01.png\",\"out\":{\"css\":{\"width\":\"337\",\"height\":\"101\",\"top\":\"218\",\"left\":\"242\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"337\",\"height\":\"101\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\",\"fontFamily\":\"\",\"fontSize\":\"\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInLeft\",\"duration\":\"1\",\"delay\":\"2.5\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/f48f8f0be602917c9d05d697b358533c.png\",\"out\":{\"css\":{\"width\":\"183\",\"height\":\"184\",\"top\":\"373\",\"left\":\"25\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"183\",\"height\":\"184\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateInDownLeft\",\"duration\":\"1.5\",\"delay\":\"3\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/b182d86e48ff4586594cd94748109c62.png\",\"out\":{\"css\":{\"width\":\"147\",\"height\":\"149\",\"top\":\"399\",\"left\":\"54\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"-2\",\"difX\":\"0\",\"margintop\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"147\",\"height\":\"149\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateInDownRight\",\"duration\":\"1.5\",\"delay\":\"3.5\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/3e6dcb9b61e87b91a6c82888e8d4283d.png\",\"out\":{\"css\":{\"width\":\"200\",\"height\":\"36\",\"top\":\"381\",\"left\":\"242\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"6\",\"difX\":\"-2\",\"margintop\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"200\",\"height\":\"36\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\",\"fontFamily\":\"\",\"fontSize\":\"\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"fadeInLeft\",\"duration\":\"1\",\"delay\":\"4\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/7d1bcad57a2e14826187cbbbc58e62ad.png\",\"out\":{\"css\":{\"width\":\"338\",\"height\":\"101\",\"top\":\"436\",\"left\":\"239\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"338\",\"height\":\"101\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInLeft\",\"duration\":\"1\",\"delay\":\"4.5\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/2381a334e38fb48fb4ef7da50076ef24.png\",\"out\":{\"css\":{\"width\":\"200\",\"height\":\"37\",\"top\":\"624\",\"left\":\"242\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"-7\",\"difX\":\"-5\",\"margintop\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"200\",\"height\":\"37\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInLeft\",\"duration\":\"1\",\"delay\":\"6\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/2b766aa094ee283fbb7058f6696dfea5.png\",\"out\":{\"css\":{\"width\":\"364\",\"height\":\"101\",\"top\":\"676\",\"left\":\"242\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"364\",\"height\":\"101\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInLeft\",\"duration\":\"1\",\"delay\":\"6.5\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/b991410fe4a026290cd4c93861a25a59.png\",\"out\":{\"css\":{\"width\":\"183\",\"height\":\"184\",\"top\":\"608\",\"left\":\"25\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"183\",\"height\":\"184\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateInDownLeft\",\"duration\":\"1.5\",\"delay\":\"5\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/3087a78467375017a92e2df7c0231ae6.png\",\"out\":{\"css\":{\"width\":\"149\",\"height\":\"148\",\"top\":\"635\",\"left\":\"51\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"-1\",\"difX\":\"-2\",\"margintop\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"149\",\"height\":\"148\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateInDownRight\",\"duration\":\"1.5\",\"delay\":\"5.5\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"特邀嘉宾\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"296\",\"height\":\"64\",\"top\":\"66\",\"left\":\"171\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"296\",\"height\":\"64\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#1abd9b\",\"fontSize\":\"36px\",\"lineHeight\":\"1.2\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateIn\",\"duration\":\"1.5\",\"delay\":\"3\",\"count\":\"1\"}]}]},{\"id\":27634,\"sceneid\":979,\"scenecode\":\"CYmE5b5PX6va\",\"pagenum\":4,\"create_time\":\"2017-07-24 14:12:01\",\"pagename\":\"新建页面\",\"userid\":0,\"thumb\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/bg\\/CYmE5b5PX6va_page3.jpg\",\"pagebg\":{\"backgroundColor\":\"#ffffff\",\"backgroundImage\":\"\"},\"update_time\":\"2018-01-29 15:51:09\",\"is_tpl\":0,\"pageanim\":{\"type\":\"\",\"interval\":0,\"duration\":0,\"autoplay\":\"\"},\"elements\":[{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/8374d050849703e7b43ed83076d6f584.png\",\"out\":{\"css\":{\"width\":\"620\",\"height\":\"617\",\"top\":\"-214\",\"left\":\"20\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"620\",\"height\":\"617\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInUp\",\"duration\":\"1.5\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/e41f9086fdd79c0e18c66a5be272d4be.png\",\"out\":{\"css\":{\"width\":\"620\",\"height\":\"617\",\"top\":\"776\",\"left\":\"12\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"620\",\"height\":\"617\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInDown\",\"duration\":\"1.5\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/c7bdd69cd061de67ecd1be45a09617ba.png\",\"out\":{\"css\":{\"width\":\"312\",\"height\":\"312\",\"top\":\"200\",\"left\":\"238\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"312\",\"height\":\"312\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInDown\",\"duration\":\"1.5\",\"delay\":\"2\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/292b4f675e28a07b4b8c6fc0163ec4fa.png\",\"out\":{\"css\":{\"width\":\"181\",\"height\":\"181\",\"top\":\"206\",\"left\":\"113\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"181\",\"height\":\"181\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInRight\",\"duration\":\"1.5\",\"delay\":\"2.5\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/9a2cfa36b864114e57b01396f9693ffe.jpg\",\"out\":{\"css\":{\"width\":\"166\",\"height\":\"171\",\"top\":\"212\",\"left\":\"119\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"100px\",\"difY\":\"2\",\"difX\":\"-3\",\"margintop\":\"0px\",\"isSc\":\"\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"166\",\"height\":\"171\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"100px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\",\"fontFamily\":\"\",\"fontSize\":\"\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"zoomIn\",\"duration\":\"1.5\",\"delay\":\"3\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/95f3e10e69393a3bf73cd8e19d2428c7.png\",\"out\":{\"css\":{\"width\":\"312\",\"height\":\"312\",\"top\":\"507\",\"left\":\"73\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"312\",\"height\":\"312\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInDown\",\"duration\":\"1.5\",\"delay\":\"4.5\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/d0651206bef1f53d8cff301da12c4bee.png\",\"out\":{\"css\":{\"width\":\"181\",\"height\":\"181\",\"top\":\"638\",\"left\":\"349\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"181\",\"height\":\"181\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInLeft\",\"duration\":\"1.5\",\"delay\":\"5\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/d069a9bcc3b0435ab941c2cfbdeef9b7.png\",\"out\":{\"css\":{\"width\":\"169\",\"height\":\"166\",\"top\":\"645\",\"left\":\"358\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"100px\",\"difY\":\"-1\",\"difX\":\"3\",\"margintop\":\"0px\",\"isSc\":\"\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"169\",\"height\":\"166\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"100px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"zoomIn\",\"duration\":\"1.5\",\"delay\":\"5.5\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"Tom\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"221\",\"height\":\"64\",\"top\":\"233\",\"left\":\"286\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"221\",\"height\":\"64\",\"backgroundColor\":\"\",\"fontFamily\":\"fangsong\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#000\",\"fontSize\":\"36px\",\"lineHeight\":\"1.8\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInNormal\",\"duration\":\"1\",\"delay\":\"4\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"著名IT公司ＣＥＯ\\n安好投资公司\\n首席顾问\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"264\",\"height\":\"124\",\"top\":\"318\",\"left\":\"275\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"74\",\"difX\":\"0\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"264\",\"height\":\"124\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#000\",\"fontSize\":\"28px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"fadeInNormal\",\"duration\":\"1\",\"delay\":\"4\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"著名IT公司ＣＥＯ\\n安好投资公司\\n首席顾问\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"253\",\"height\":\"124\",\"top\":\"650\",\"left\":\"92\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"-4\",\"difX\":\"-11\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"253\",\"height\":\"124\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#000\",\"fontSize\":\"28px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInNormal\",\"duration\":\"1\",\"delay\":\"6\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"Judith\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"221\",\"height\":\"64\",\"top\":\"574\",\"left\":\"113\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"221\",\"height\":\"64\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#000\",\"fontSize\":\"36px\",\"lineHeight\":\"1.8\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInNormal\",\"duration\":\"1\",\"delay\":\"6\",\"count\":\"1\"}]}]},{\"id\":27635,\"sceneid\":979,\"scenecode\":\"CYmE5b5PX6va\",\"pagenum\":5,\"create_time\":\"2017-07-24 14:12:03\",\"pagename\":\"新建页面\",\"userid\":0,\"thumb\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/bg\\/CYmE5b5PX6va_page4.jpg\",\"pagebg\":{\"backgroundColor\":\"#ffffff\",\"backgroundImage\":\"\"},\"update_time\":\"2018-01-29 15:51:09\",\"is_tpl\":0,\"pageanim\":{\"type\":\"\",\"interval\":0,\"duration\":0,\"autoplay\":\"\"},\"elements\":[{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/fb7c52e1a7853e6c409adbc7cb44cbbc.png\",\"out\":{\"css\":{\"width\":\"576\",\"height\":\"864\",\"top\":\"68\",\"left\":\"32\",\"opacity\":\"0.41\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"576\",\"height\":\"864\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\",\"fontFamily\":\"\",\"fontSize\":\"\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateInDownRight\",\"duration\":\"1.5\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/3a7c3add7bf344d849c10e4033ab4b6f.png\",\"out\":{\"css\":{\"width\":\"620\",\"height\":\"617\",\"top\":\"184\",\"left\":\"10\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"620\",\"height\":\"617\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"zoomIn\",\"duration\":\"1.5\",\"delay\":\"2\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/f8db3ace0245fa9f1519c7ac24611155.png\",\"out\":{\"css\":{\"width\":\"540\",\"height\":\"825\",\"top\":\"91\",\"left\":\"50\",\"opacity\":\"0.41\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"540\",\"height\":\"825\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\",\"fontFamily\":\"\",\"fontSize\":\"\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"rotateInDownLeft\",\"duration\":\"1.5\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"会议流程\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"296\",\"height\":\"64\",\"top\":\"103\",\"left\":\"172\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"296\",\"height\":\"64\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#1abd9b\",\"fontSize\":\"36px\",\"lineHeight\":\"1.2\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateIn\",\"duration\":\"1.5\",\"delay\":\"3\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"8:00会议签到\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"270\",\"height\":\"65\",\"top\":\"207\",\"left\":\"203\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"difY\":\"35\",\"difX\":\"0\"}},\"in\":{\"css\":{\"width\":\"270\",\"height\":\"65\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0px\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"bold\",\"fontDecoration\":\"none\",\"textAlign\":\"left\",\"textValign\":\"middle\",\"color\":\"#fff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letterSpacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"fadeInLeft\",\"duration\":\"3\",\"delay\":\"1\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"9:00会议开始\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"281\",\"height\":\"59\",\"top\":\"298\",\"left\":\"200\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"difY\":\"8\",\"difX\":\"-2\"}},\"in\":{\"css\":{\"width\":\"281\",\"height\":\"59\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0px\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"bold\",\"fontDecoration\":\"none\",\"textAlign\":\"left\",\"textValign\":\"middle\",\"color\":\"#fff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letterSpacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"fadeInLeft\",\"duration\":\"3\",\"delay\":\"1\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"10:30案例分享会\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"328\",\"height\":\"70\",\"top\":\"392\",\"left\":\"189\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"difY\":\"-16\",\"difX\":\"0\"}},\"in\":{\"css\":{\"width\":\"328\",\"height\":\"70\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0px\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"bold\",\"fontDecoration\":\"none\",\"textAlign\":\"left\",\"textValign\":\"middle\",\"color\":\"#fff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letterSpacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"fadeInLeft\",\"duration\":\"3\",\"delay\":\"1\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"12:00会议午餐\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"341\",\"height\":\"58\",\"top\":\"490\",\"left\":\"185\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"difY\":\"13\",\"difX\":\"0\"}},\"in\":{\"css\":{\"width\":\"341\",\"height\":\"58\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0px\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"bold\",\"fontDecoration\":\"none\",\"textAlign\":\"left\",\"textValign\":\"middle\",\"color\":\"#fff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letterSpacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"fadeInLeft\",\"duration\":\"3\",\"delay\":\"1\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"13:00参观办公区域\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"461\",\"height\":\"80\",\"top\":\"601\",\"left\":\"184\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"difY\":\"2\",\"difX\":\"6\"}},\"in\":{\"css\":{\"width\":\"461\",\"height\":\"80\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0px\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"bold\",\"fontDecoration\":\"none\",\"textAlign\":\"left\",\"textValign\":\"middle\",\"color\":\"#fff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letterSpacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"fadeInLeft\",\"duration\":\"3\",\"delay\":\"1\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"15:00下午茶\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"337\",\"height\":\"66\",\"top\":\"693\",\"left\":\"194\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"difY\":\"-160\",\"difX\":\"13\"}},\"in\":{\"css\":{\"width\":\"337\",\"height\":\"66\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0px\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"bold\",\"fontDecoration\":\"none\",\"textAlign\":\"left\",\"textValign\":\"middle\",\"color\":\"#fff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letterSpacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"fadeInLeft\",\"duration\":\"3\",\"delay\":\"1\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"top\":\"1057\",\"left\":\"27\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"215\",\"difX\":\"-156\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#ffffff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"zoomIn\",\"duration\":\"1.5\",\"delay\":\"4\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"top\":\"1057\",\"left\":\"27\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"215\",\"difX\":\"-156\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#ffffff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"zoomIn\",\"duration\":\"1.5\",\"delay\":\"4\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"top\":\"1057\",\"left\":\"27\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"215\",\"difX\":\"-156\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#ffffff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"zoomIn\",\"duration\":\"1.5\",\"delay\":\"4\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"top\":\"1057\",\"left\":\"27\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"215\",\"difX\":\"-156\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#ffffff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"zoomIn\",\"duration\":\"1.5\",\"delay\":\"4\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"top\":\"1057\",\"left\":\"27\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"215\",\"difX\":\"-156\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#ffffff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"zoomIn\",\"duration\":\"1.5\",\"delay\":\"4\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"top\":\"1057\",\"left\":\"27\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"215\",\"difX\":\"-156\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#ffffff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"zoomIn\",\"duration\":\"1.5\",\"delay\":\"4\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"top\":\"1057\",\"left\":\"27\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"215\",\"difX\":\"-156\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#ffffff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"zoomIn\",\"duration\":\"1.5\",\"delay\":\"4\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"top\":\"1057\",\"left\":\"27\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"215\",\"difX\":\"-156\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#ffffff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"zoomIn\",\"duration\":\"1.5\",\"delay\":\"4\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"top\":\"1057\",\"left\":\"27\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"215\",\"difX\":\"-156\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#ffffff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"zoomIn\",\"duration\":\"1.5\",\"delay\":\"4\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"top\":\"1057\",\"left\":\"27\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"215\",\"difX\":\"-156\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#ffffff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"zoomIn\",\"duration\":\"1.5\",\"delay\":\"4\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"top\":\"1057\",\"left\":\"27\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"215\",\"difX\":\"-156\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"79\",\"height\":\"119\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#ffffff\",\"fontSize\":\"40px\",\"lineHeight\":\"1.5\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"zoomIn\",\"duration\":\"1.5\",\"delay\":\"4\",\"count\":\"1\"}]}]},{\"id\":27636,\"sceneid\":979,\"scenecode\":\"CYmE5b5PX6va\",\"pagenum\":6,\"create_time\":\"2017-07-24 14:12:05\",\"pagename\":\"新建页面\",\"userid\":0,\"thumb\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/bg\\/CYmE5b5PX6va_page5.jpg\",\"pagebg\":{\"backgroundColor\":\"#ffffff\",\"backgroundImage\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/bg\\/10cc8464b43786066f656d5d975a46ec.jpg\"},\"update_time\":\"2018-01-29 15:51:09\",\"is_tpl\":0,\"pageanim\":{\"type\":\"\",\"interval\":0,\"duration\":0,\"autoplay\":\"\"},\"elements\":[{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/7419e834bf2f64f8b394cfde014fd6f7.jpg\",\"out\":{\"css\":{\"width\":\"441\",\"height\":\"353\",\"top\":\"108\",\"left\":\"98\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"441\",\"height\":\"353\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateInDownRight\",\"duration\":\"1\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/692b334040c88e3e5f8af5d4c4726076.png\",\"out\":{\"css\":{\"width\":\"441\",\"height\":\"353\",\"top\":\"108\",\"left\":\"99\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"441\",\"height\":\"353\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInNormal\",\"duration\":\"2\",\"delay\":\"3\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/b6990c0e7d5329515ab4d2745574a1ce.png\",\"out\":{\"css\":{\"width\":\"451\",\"height\":\"367\",\"top\":\"101\",\"left\":\"92\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"0\",\"difX\":\"-3\",\"margintop\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"451\",\"height\":\"367\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInNormal\",\"duration\":\"1\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/a8f032d2c6f681f3922cba3d856a201b.jpg\",\"out\":{\"css\":{\"width\":\"452\",\"height\":\"364\",\"top\":\"503\",\"left\":\"93\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"452\",\"height\":\"364\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateInDownLeft\",\"duration\":\"1\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/353bc8d6303bb43b49ae63f47556f97e.jpg\",\"out\":{\"css\":{\"width\":\"452\",\"height\":\"364\",\"top\":\"503\",\"left\":\"93\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"452\",\"height\":\"364\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInNormal\",\"duration\":\"2\",\"delay\":\"4\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/a445c7ce31d06c1f30dc926592a4ac89.png\",\"out\":{\"css\":{\"width\":\"463.99\",\"height\":\"383.987\",\"top\":\"496\",\"left\":\"92\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":-8,\"difX\":0,\"margintop\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"463.99\",\"height\":\"383.987\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\"},\"properties\":[]},\"anim\":[{\"type\":\"fadeInNormal\",\"duration\":\"1\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"会议美食\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"214\",\"height\":\"64\",\"top\":\"40\",\"left\":\"217\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"isSc\":\"\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"214\",\"height\":\"64\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#0baa97\",\"fontSize\":\"36px\",\"lineHeight\":\"1.8\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateIn\",\"duration\":\"1\",\"delay\":\"1.2\",\"count\":\"1\"}]}]},{\"id\":27637,\"sceneid\":979,\"scenecode\":\"CYmE5b5PX6va\",\"pagenum\":7,\"create_time\":\"2017-07-24 14:12:06\",\"pagename\":\"新建页面\",\"userid\":0,\"thumb\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/bg\\/CYmE5b5PX6va_page6.jpg\",\"pagebg\":{\"backgroundColor\":\"#ffffff\",\"backgroundImage\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/bg\\/10cc8464b43786066f656d5d975a46ec.jpg\"},\"update_time\":\"2018-01-29 15:51:09\",\"is_tpl\":0,\"pageanim\":{\"type\":\"\",\"interval\":0,\"duration\":0,\"autoplay\":\"\"},\"elements\":[{\"type\":\"2\",\"content\":\"Time&Place\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"218\",\"height\":\"64\",\"top\":\"36\",\"left\":\"61\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"6\",\"difX\":\"-308\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"218\",\"height\":\"64\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"left\",\"textValign\":\"middle\",\"color\":\"#0baa97\",\"fontSize\":\"36px\",\"lineHeight\":\"1.8\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"rotateInDownLeft\",\"duration\":\"1.5\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"时间和地点\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"214\",\"height\":\"64\",\"top\":\"79\",\"left\":\"52\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"214\",\"height\":\"64\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#0baa97\",\"fontSize\":\"36px\",\"lineHeight\":\"1.8\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateInDownRight\",\"duration\":\"1.5\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/202785422256937ac9713a49.76647241.jpg\",\"out\":{\"css\":{\"width\":\"569\",\"height\":\"839\",\"top\":\"160\",\"left\":\"32\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"81\",\"difX\":\"8\",\"margintop\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"569\",\"height\":\"839\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\",\"fontFamily\":\"\",\"fontSize\":\"\"},\"properties\":[]},\"anim\":[{\"type\":\"zoomIn\",\"duration\":\"1.5\",\"delay\":\"2\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"时间：6月15日\\n地点：安徽省合肥市包河区绿地赢海A座\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"527\",\"height\":\"189\",\"top\":\"760\",\"left\":\"41\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"14\",\"difX\":\"-4\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"527\",\"height\":\"189\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"left\",\"textValign\":\"middle\",\"color\":\"#fff\",\"fontSize\":\"36px\",\"lineHeight\":\"1.8\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":0}},\"anim\":[{\"type\":\"fadeInLeft\",\"duration\":\"1.5\",\"delay\":\"3\",\"count\":\"1\"}]}]},{\"id\":27638,\"sceneid\":979,\"scenecode\":\"CYmE5b5PX6va\",\"pagenum\":8,\"create_time\":\"2017-07-24 14:12:07\",\"pagename\":\"新建页面\",\"userid\":0,\"thumb\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/bg\\/CYmE5b5PX6va_page7.jpg\",\"pagebg\":{\"backgroundColor\":\"#ffffff\",\"backgroundImage\":\"\"},\"update_time\":\"2018-01-29 15:51:09\",\"is_tpl\":0,\"pageanim\":{\"type\":\"\",\"interval\":0,\"duration\":0,\"autoplay\":\"\"},\"elements\":[{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/8374d050849703e7b43ed83076d6f584.png\",\"out\":{\"css\":{\"width\":\"620\",\"height\":\"617\",\"top\":\"-289\",\"left\":\"16\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"620\",\"height\":\"617\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\",\"fontFamily\":\"\",\"fontSize\":\"\",\"borderWidth\":\"0px\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateIn\",\"duration\":\"2\",\"delay\":\"0.6\",\"count\":\"1\"}]},{\"type\":\"4\",\"content\":\"\",\"src\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/img\\/e41f9086fdd79c0e18c66a5be272d4be.png\",\"out\":{\"css\":{\"width\":\"620\",\"height\":\"617\",\"top\":\"808\",\"left\":\"14\",\"opacity\":\"0.57\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"620\",\"height\":\"617\",\"top\":\"0\",\"left\":\"0\",\"borderRadius\":\"0px\",\"boxShadow\":\"black 0px 0px 0px\",\"color\":\"rgba(0,0,0,0)px\",\"bolderWidth\":\"0px\",\"borderColor\":\"none\",\"borderStyle\":\"none\",\"fontSize\":\"\"},\"properties\":[]},\"anim\":[{\"type\":\"rotateIn\",\"duration\":\"1.5\",\"delay\":\"2\",\"count\":\"1\"}]},{\"type\":\"2\",\"content\":\"接受邀请\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"267\",\"height\":\"72\",\"top\":\"310\",\"left\":\"197\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"borderRadius\":\"0px\",\"difY\":\"-111\",\"difX\":\"0\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"267\",\"height\":\"72\",\"backgroundColor\":\"\",\"fontFamily\":\"yuanhei\",\"bolderWidth\":\"0pxpx\",\"borderColor\":\"#000000\",\"borderStyle\":\"none\",\"borderRadius\":\"0px\",\"fontStyle\":\"normal\",\"fontWeight\":\"normal\",\"fontDecoration\":\"none\",\"textAlign\":\"center\",\"textValign\":\"middle\",\"color\":\"#45d1d7\",\"fontSize\":\"36px\",\"lineHeight\":\"1.2\",\"boxShadow\":\"black 0px 0px 0\",\"letter-spacing\":\"0px\",\"top\":\"0\",\"left\":\"0\"},\"properties\":{\"hasUrl\":\"0\"}},\"anim\":[{\"type\":\"fadeInDown\",\"duration\":\"1\",\"delay\":\"1.6\",\"count\":\"1\"}]},{\"type\":\"i\",\"id\":1507799550057,\"inputType\":\"name\",\"moduleType\":\"contact\",\"formid\":27638,\"content\":\"\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"400\",\"height\":\"72\",\"top\":\"398\",\"left\":\"116\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\",\"isSc\":false},\"properties\":[]},\"in\":{\"css\":{\"width\":\"400\",\"height\":\"72\",\"borderWidth\":\"2px\",\"borderColor\":\"#45d1d7\",\"borderStyle\":\"solid\",\"boxshadow\":\"0\",\"background\":\"#ffffff\",\"borderRadius\":\"0px\",\"color\":\"#4B4B4B \",\"fontSize\":\"28px\",\"lineHeight\":\"72px\",\"padding\":\"20px\"},\"properties\":{\"placeholder\":\"姓名\",\"regularType\":\"normal\",\"require\":\"1\"}},\"anim\":[]},{\"type\":\"i\",\"id\":1507799557505,\"inputType\":\"telephone\",\"moduleType\":\"contact\",\"formid\":27638,\"content\":\"\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"400\",\"height\":\"72\",\"top\":\"490\",\"left\":\"115\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"400\",\"height\":\"72\",\"borderWidth\":\"2px\",\"borderColor\":\"#45d1d7\",\"borderStyle\":\"solid\",\"boxshadow\":\"0\",\"background\":\"#ffffff\",\"borderRadius\":\"0px\",\"color\":\"#4B4B4B \",\"fontSize\":\"28px\",\"lineHeight\":\"72px\",\"padding\":\"20px\"},\"properties\":{\"placeholder\":\"手机\",\"regularType\":\"normal\",\"require\":\"1\"}},\"anim\":[]},{\"type\":\"i\",\"id\":1507799566722,\"inputType\":\"name\",\"moduleType\":\"contact\",\"formid\":27638,\"content\":\"\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"400\",\"height\":\"72\",\"top\":\"584\",\"left\":\"115\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"400\",\"height\":\"72\",\"borderWidth\":\"2px\",\"borderColor\":\"#45d1d7\",\"borderStyle\":\"solid\",\"boxshadow\":\"0\",\"background\":\"#ffffff\",\"borderRadius\":\"0px\",\"color\":\"#4B4B4B \",\"fontSize\":\"28px\",\"lineHeight\":\"72px\",\"padding\":\"20px\"},\"properties\":{\"placeholder\":\"人数\",\"regularType\":\"normal\",\"require\":\"1\"}},\"anim\":[]},{\"type\":\"s\",\"id\":1507799610865,\"inputType\":\"submit\",\"moduleType\":\"submit\",\"formid\":27638,\"content\":\"\",\"src\":\"\",\"out\":{\"css\":{\"width\":\"400\",\"height\":\"72\",\"top\":\"673\",\"left\":\"117\",\"opacity\":\"1\",\"transform\":\"rotate(0deg)\"},\"properties\":[]},\"in\":{\"css\":{\"width\":\"400\",\"height\":\"72\",\"borderWidth\":\"0px\",\"borderColor\":\"#ffffff\",\"borderStyle\":\"solid\",\"boxshadow\":\"0\",\"textAlign\":\"center\",\"background\":\"#45d1d7\",\"borderRadius\":\"8px\",\"color\":\"#fff\",\"fontSize\":\"32px\",\"lineHeight\":\"72px\"},\"properties\":{\"str\":\"提交\",\"ok\":\"感谢你的填写\"}},\"anim\":[]}]}],\"sceneinfo\":{\"id\":979,\"name\":\" 清新 简洁 大方 高大上 科技 学术 商务邀请函 \",\"code\":\"CYmE5b5PX6va\",\"desc\":\"邀请函&nbsp;会议邀请&nbsp;清新&nbsp;简洁&nbsp;大方&nbsp;高大上&nbsp;科技&nbsp;学术&nbsp;商务&nbsp;蓝绿色系\",\"amount\":\"49.00\",\"thumb_list\":\"https:\\/\\/res.zhangu365.com\\/user\\/2017\\/0725\\/9eb16105e01dae930359055a823c9be8.jpg\",\"thumb_show\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/scene\\/thumb_220_220_59758fac2c4ac.jpg\",\"musicname\":\"加油打气\",\"music\":\"https:\\/\\/res.zhangu365.com\\/syspic\\/mp3\\/jiayoudaqi.mp3\",\"catid\":125,\"userid\":10,\"hits\":17289,\"favorites\":0,\"follows\":109,\"use_num\":7011,\"create_time\":\"2017-07-24 14:11:56\",\"update_time\":\"2018-01-29 15:51:09\",\"publish_time\":\"2017-04-12 16:05:56\",\"del_time\":0,\"create_ip\":\"\",\"is_tpl\":1,\"is_free\":2,\"pid\":0,\"tags\":\"\",\"page_mode\":1,\"is_loop\":1,\"autoplay\":0,\"is_page\":1,\"special\":608,\"bigspecial_id\":0,\"is_index\":0,\"is_systpl\":1,\"index_img\":\"https:\\/\\/res.zhangu365.com\\/designer\\/2018\\/0329\\/040d97692c61d07ca108023642045c7a.png\",\"sort\":1593,\"version\":0,\"loding\":\"https:\\/\\/www.zhangu365.com\\/static\\/images\\/home\\/preview\\/loadlogo.png\"},\"additional\":{\"type\":1,\"ismobile\":0}}}");
                            // i.$store.dispatch("initOriginData", initJsonData.data);
                        }
                    },
                    created: function() {
                        this.initOriginData()
                    }
                },
                f = {
                    /*render: function() {
                       var t = this.$createElement,
                       e = this._self._c || t;
                       return e("div", {
                           attrs: {
                               id: "main"
                           }
                       },
                       [e("transition", {
                           attrs: {
                               name: "fade2"
                           }
                       },
                       [this.showLoading ? e("div", {
                           staticClass: "prelogo"
                       },
                       [e("img", {
                           attrs: {
                               src: "" == this.sceneInfoSub.loding ? "../assets/images/logo.png": this.sceneInfoSub.loding
                           }
                       })]) : this._e()]), this._v(" "), this.showLoading ? this._e() : e("main-page")], 1)
                   },
                   staticRenderFns: []*/
                };
            var g = i("VU/8")(h, f, !1,
                function(t) {
                    i("lLer")
                },
                null, null).exports,
                v = i("/ocq");
            r.
            default.use(v.a);
            var w, m = new v.a({
                    mode: "history",
                    routes: [{
                        path: "/",
                        name: "index",
                        component: g
                    }]
                }),
                y = i("bOdI"),
                x = i.n(y),
                b = function(t, e) { (0, t.commit)("INITORIGINDATA", e)
                },
                S = function(t, e) {
                    var i = t.commit;
                    t.state;
                    i("SELECTPAGE", e)
                },
                _ = function(t) {
                    return t.originData
                },
                C = function(t) {
                    return t.originData.pages
                },
                k = function(t) {
                    return t.originData.pages[t.currentPage - 1].pagebg
                },
                I = function(t) {
                    return t.currentPage
                },
                z = function(t) {
                    return t.originData.sceneinfo
                },
                T = function(t) {
                    return t.originData.sceneinfo
                },
                W = function(t) {
                    return t.originData.additional
                };
            r.
            default.use(a.a);
            var N = (w = {},
                    x()(w, "INITORIGINDATA",
                        function(t, e) {
                            for (var i = 0; i < e.lists.length; i++) for (var s = 0; s < e.lists[i].elements.length; s++) 4 == e.lists[i].elements[s].type && void 0 == e.lists[i].elements[s].cropData && (e.lists[i].elements[s].cropData = {
                                x: 0,
                                y: 0,
                                width: parseInt(e.lists[i].elements[s].out.css.width),
                                height: parseInt(e.lists[i].elements[s].out.css.height)
                            });
                            t.originData.pages = e.lists,
                                t.originData.sceneinfo = e.sceneinfo,
                                t.originData.additional = e.additional
                        }), x()(w, "SELECTPAGE",
                function(t, e) {
                    t.currentPage = e
                }), w),
                O = new a.a.Store({
                    actions: s,
                    getters: n,
                    mutations: N,
                    state: {
                        currentPage: 1,
                        originData: {
                            svgColors: {},
                            sceneinfo: {},
                            pages: [{
                                id: 837949,
                                sceneId: 107961,
                                num: 1,
                                name: "",
                                pagebg: {},
                                elements: []
                            }],
                            additional: {}
                        }
                    },
                    strict: !1
                }),
                D = i("mtWM"),
                $ = i.n(D),
                M = i("BTaQ"),
                P = i.n(M),
                H = (i("+skl"), i("v2ns"), i("F3EI")),
                R = i.n(H);
            r.
                default.config.productionTip = !1,
                i("llnD"),
                i("DV4W"),
                r.
                default.use(a.a),
                r.
                default.use(P.a),
                r.
                default.use(R.a),
                $.a.defaults.withCredentials = !0,
                r.
                    default.prototype.$http = $.a,
                new r.
                default(
                    /*{
                    el:
                        "#main",
                    router: m,
                    store: O,
                    components: {
                        Index: g
                    },
                    template: "<Index/>"
                }*/
                    )
        },
        gyMJ: function(t, e, i) {
            "use strict";
            e.a = {
                getScenepageBySceneID: function(t) {
                    if ( - 1 == window.location.href.indexOf("www.zhangu365.com")) if ( - 1 == window.location.href.indexOf("www.ezhangu.com")) var e = "https://ezhangu.com/editor/v1/";
                    else e = "https://www.ezhangu.com/editor/v1/";
                    else e = "https://www.zhangu365.com/editor/v1/";
                    return e + "scene/getscene?id=" + t
                },
                getPreview: function(t, e) {
                    if ( - 1 == window.location.href.indexOf("www.zhangu365.com")) if ( - 1 == window.location.href.indexOf("www.ezhangu.com")) var i = "https://ezhangu.com/editor/v1/";
                    else i = "https://www.ezhangu.com/editor/v1/";
                    else i = "https://www.zhangu365.com/editor/v1/";
                    return i + "index/showscene?type=" + t + "&code=" + e
                },
                submitZan: function() {
                    if ( - 1 == window.location.href.indexOf("www.zhangu365.com")) if ( - 1 == window.location.href.indexOf("www.ezhangu.com")) var t = "https://ezhangu.com/editor/v1/";
                    else t = "https://www.ezhangu.com/editor/v1/";
                    else t = "https://www.zhangu365.com/editor/v1/";
                    return t + "index/praise"
                },
                getZanCount: function() {
                    if ( - 1 == window.location.href.indexOf("www.zhangu365.com")) if ( - 1 == window.location.href.indexOf("www.ezhangu.com")) var t = "https://ezhangu.com/editor/v1/";
                    else t = "https://www.ezhangu.com/editor/v1/";
                    else t = "https://www.zhangu365.com/editor/v1/";
                    return t + "index/praisecount"
                },
                submitForm: function() {
                    if ( - 1 == window.location.href.indexOf("www.zhangu365.com")) if ( - 1 == window.location.href.indexOf("www.ezhangu.com")) var t = "https://ezhangu.com/editor/v1/";
                    else t = "https://www.ezhangu.com/editor/v1/";
                    else t = "https://www.zhangu365.com/editor/v1/";
                    return t + "index/submitform"
                },
                resimg: function() {
                    if ( - 1 == window.location.href.indexOf("www.zhangu365.com")) if ( - 1 == window.location.href.indexOf("www.ezhangu.com")) var t = "https://ezhangu.com/editor/v1/";
                    else t = "https://www.ezhangu.com/editor/v1/";
                    else t = "https://www.zhangu365.com/editor/v1/";
                    return t + "index/resimg"
                },
                viewDefault: function() {
                    if ( - 1 == window.location.href.indexOf("www.zhangu365.com")) if ( - 1 == window.location.href.indexOf("www.ezhangu.com")) var t = "https://ezhangu.com/editor/v1/";
                    else t = "https://www.ezhangu.com/editor/v1/";
                    else t = "https://www.zhangu365.com/editor/v1/";
                    return t + "statdata/viewDetailAdd"
                },
                buttonClick: function() {
                    if ( - 1 == window.location.href.indexOf("www.zhangu365.com")) if ( - 1 == window.location.href.indexOf("www.ezhangu.com")) var t = "https://ezhangu.com/editor/v1/";
                    else t = "https://www.ezhangu.com/editor/v1/";
                    else t = "https://www.zhangu365.com/editor/v1/";
                    return t + "statdata/hdfx"
                },
                leaveUrl: function() {
                    if ( - 1 == window.location.href.indexOf("www.zhangu365.com")) if ( - 1 == window.location.href.indexOf("www.ezhangu.com")) var t = "https://ezhangu.com/editor/v1/";
                    else t = "https://www.ezhangu.com/editor/v1/";
                    else t = "https://www.zhangu365.com/editor/v1/";
                    return t + "statdata/viewLeave"
                }
            }
        },
        jqc1: function(t, e) {},
        lG8U: function(t, e) {},
        lLer: function(t, e) {},
        llnD: function(t, e) {},
        pYmz: function(t, e, i) {
            "use strict";
            Object.defineProperty(e, "__esModule", {
                value: !0
            });
            var s = "undefined" != typeof window;
            s && (window.Swiper = i("gsqX"));
            var n = {
                    name: "swiper",
                    props: {
                        options: {
                            type: Object,
                            default:
                                function() {
                                    return {
                                        autoplay:
                                            3500
                                    }
                                }
                        },
                        notNextTick: {
                            type: Boolean,
                            default:
                                function() {
                                    return ! 1
                                }
                        }
                    },
                    data: function() {
                        return {
                            defaultSwiperClasses: {
                                wrapperClass: "swiper-wrapper"
                            }
                        }
                    },
                    ready: function() { ! this.swiper && s && (this.swiper = new Swiper(this.$el, this.options))
                    },
                    mounted: function() {
                        var t = this,
                            e = function() {
                                if (!t.swiper && s) {
                                    delete t.options.notNextTick;
                                    var e = !1;
                                    for (var i in t.defaultSwiperClasses) t.defaultSwiperClasses.hasOwnProperty(i) && t.options[i] && (e = !0, t.defaultSwiperClasses[i] = t.options[i]);
                                    var n = function() {
                                        t.swiper = new Swiper(t.$el, t.options)
                                    };
                                    e ? t.$nextTick(n) : n()
                                }
                            } (this.options.notNextTick || this.notNextTick) ? e() : this.$nextTick(e)
                    },
                    updated: function() {
                        this.swiper && this.swiper.update()
                    },
                    beforeDestroy: function() {
                        this.swiper && (this.swiper.destroy(), delete this.swiper)
                    }
                },
                r = {
                    render: function() {
                        var t = this,
                            e = t.$createElement,
                            i = t._self._c || e;
                        return i("div", {
                                staticClass: "swiper-container"
                            },
                            [t._t("parallax-bg"), t._v(" "), i("div", {
                                    class: t.defaultSwiperClasses.wrapperClass
                                },
                                [t._t("default")], 2), t._v(" "), t._t("pagination"), t._v(" "), t._t("button-prev"), t._v(" "), t._t("button-next"), t._v(" "), t._t("scrollbar")], 2)
                    },
                    staticRenderFns: []
                },
                a = i("VU/8")(n, r, !1, null, null, null);
            e.
                default = a.exports
        },
        v2ns: function(t, e) {}
    },
    ["NHnr"]);
