!function (e) {
    "use strict";
    if ("function" == typeof define && define.amd) define(["jquery", "moment"], e); else if ("object" == typeof exports) module.exports = e(require("jquery"), require("moment")); else {
        if ("undefined" == typeof jQuery) throw"bootstrap-datetimepicker requires jQuery to be loaded first";
        if ("undefined" == typeof moment) throw"bootstrap-datetimepicker requires Moment.js to be loaded first";
        e(jQuery, moment)
    }
}(function ($, _) {
    "use strict";
    if (!_) throw new Error("bootstrap-datetimepicker requires Moment.js to be loaded first");

    function i(i, p) {
        function a() {
            return void 0 !== _.tz && void 0 !== p.timeZone && null !== p.timeZone && "" !== p.timeZone
        }

        function c(e) {
            var t = null == e ? _() : _.isDate(e) || _.isMoment(e) ? _(e) : a() ? _.tz(e, B, p.useStrict, p.timeZone) : _(e, B, p.useStrict);
            return a() && t.tz(p.timeZone), t
        }

        function d(e) {
            if ("string" != typeof e || 1 < e.length) throw new TypeError("isEnabled expects a single character string parameter");
            switch (e) {
                case"y":
                    return -1 !== q.indexOf("Y");
                case"M":
                    return -1 !== q.indexOf("M");
                case"d":
                    return -1 !== q.toLowerCase().indexOf("d");
                case"h":
                case"H":
                    return -1 !== q.toLowerCase().indexOf("h");
                case"m":
                    return -1 !== q.indexOf("m");
                case"s":
                    return -1 !== q.indexOf("s");
                default:
                    return !1
            }
        }

        function l() {
            return d("h") || d("m") || d("s")
        }

        function u() {
            return d("y") || d("M") || d("d")
        }

        function f() {
            var e, t, a, n = $("<div>").addClass("timepicker-hours").append($("<table>").addClass("table-condensed")),
                r = $("<div>").addClass("timepicker-minutes").append($("<table>").addClass("table-condensed")),
                i = $("<div>").addClass("timepicker-seconds").append($("<table>").addClass("table-condensed")),
                o = [(e = $("<tr>"), t = $("<tr>"), a = $("<tr>"), d("h") && (e.append($("<td>").append($("<a>").attr({
                    href: "#",
                    tabindex: "-1",
                    title: p.tooltips.incrementHour
                }).addClass("btn").attr("data-action", "incrementHours").append($("<i>").addClass(p.icons.up)))), t.append($("<td>").append($("<span>").addClass("timepicker-hour").attr({
                    "data-time-component": "hours",
                    title: p.tooltips.pickHour
                }).attr("data-action", "showHours"))), a.append($("<td>").append($("<a>").attr({
                    href: "#",
                    tabindex: "-1",
                    title: p.tooltips.decrementHour
                }).addClass("btn").attr("data-action", "decrementHours").append($("<i>").addClass(p.icons.down))))), d("m") && (d("h") && (e.append($("<td>").addClass("separator")), t.append($("<td>").addClass("separator").html(":")), a.append($("<td>").addClass("separator"))), e.append($("<td>").append($("<a>").attr({
                    href: "#",
                    tabindex: "-1",
                    title: p.tooltips.incrementMinute
                }).addClass("btn").attr("data-action", "incrementMinutes").append($("<i>").addClass(p.icons.up)))), t.append($("<td>").append($("<span>").addClass("timepicker-minute").attr({
                    "data-time-component": "minutes",
                    title: p.tooltips.pickMinute
                }).attr("data-action", "showMinutes"))), a.append($("<td>").append($("<a>").attr({
                    href: "#",
                    tabindex: "-1",
                    title: p.tooltips.decrementMinute
                }).addClass("btn").attr("data-action", "decrementMinutes").append($("<i>").addClass(p.icons.down))))), d("s") && (d("m") && (e.append($("<td>").addClass("separator")), t.append($("<td>").addClass("separator").html(":")), a.append($("<td>").addClass("separator"))), e.append($("<td>").append($("<a>").attr({
                    href: "#",
                    tabindex: "-1",
                    title: p.tooltips.incrementSecond
                }).addClass("btn").attr("data-action", "incrementSeconds").append($("<i>").addClass(p.icons.up)))), t.append($("<td>").append($("<span>").addClass("timepicker-second").attr({
                    "data-time-component": "seconds",
                    title: p.tooltips.pickSecond
                }).attr("data-action", "showSeconds"))), a.append($("<td>").append($("<a>").attr({
                    href: "#",
                    tabindex: "-1",
                    title: p.tooltips.decrementSecond
                }).addClass("btn").attr("data-action", "decrementSeconds").append($("<i>").addClass(p.icons.down))))), Y || (e.append($("<td>").addClass("separator")), t.append($("<td>").append($("<button>").addClass("btn btn-primary").attr({
                    "data-action": "togglePeriod",
                    tabindex: "-1",
                    title: p.tooltips.togglePeriod
                }))), a.append($("<td>").addClass("separator"))), $("<div>").addClass("timepicker-picker").append($("<table>").addClass("table-condensed").append([e, t, a])))];
            return d("h") && o.push(n), d("m") && o.push(r), d("s") && o.push(i), o
        }

        function t() {
            var e, t, a, n = $("<div>").addClass("bootstrap-datetimepicker-widget dropdown-menu"),
                r = $("<div>").addClass("datepicker").append((t = $("<thead>").append($("<tr>").append($("<th>").addClass("prev").attr("data-action", "previous").append($("<i>").addClass(p.icons.previous))).append($("<th>").addClass("picker-switch").attr("data-action", "pickerSwitch").attr("colspan", p.calendarWeeks ? "6" : "5")).append($("<th>").addClass("next").attr("data-action", "next").append($("<i>").addClass(p.icons.next)))), a = $("<tbody>").append($("<tr>").append($("<td>").attr("colspan", p.calendarWeeks ? "8" : "7"))), [$("<div>").addClass("datepicker-days").append($("<table>").addClass("table-condensed").append(t).append($("<tbody>"))), $("<div>").addClass("datepicker-months").append($("<table>").addClass("table-condensed").append(t.clone()).append(a.clone())), $("<div>").addClass("datepicker-years").append($("<table>").addClass("table-condensed").append(t.clone()).append(a.clone())), $("<div>").addClass("datepicker-decades").append($("<table>").addClass("table-condensed").append(t.clone()).append(a.clone()))])),
                i = $("<div>").addClass("timepicker").append(f()), o = $("<ul>").addClass("list-unstyled"),
                s = $("<li>").addClass("picker-switch" + (p.collapse ? " accordion-toggle" : "")).append((e = [], p.showTodayButton && e.push($("<td>").append($("<a>").attr({
                    "data-action": "today",
                    title: p.tooltips.today
                }).append($("<i>").addClass(p.icons.today)))), !p.sideBySide && u() && l() && e.push($("<td>").append($("<a>").attr({
                    "data-action": "togglePicker",
                    title: p.tooltips.selectTime
                }).append($("<i>").addClass(p.icons.time)))), p.showClear && e.push($("<td>").append($("<a>").attr({
                    "data-action": "clear",
                    title: p.tooltips.clear
                }).append($("<i>").addClass(p.icons.clear)))), p.showClose && e.push($("<td>").append($("<a>").attr({
                    "data-action": "close",
                    title: p.tooltips.close
                }).append($("<i>").addClass(p.icons.close)))), $("<table>").addClass("table-condensed").append($("<tbody>").append($("<tr>").append(e)))));
            return p.inline && n.removeClass("dropdown-menu"), Y && n.addClass("usetwentyfour"), d("s") && !Y && n.addClass("wider"), p.sideBySide && u() && l() ? (n.addClass("timepicker-sbs"), "top" === p.toolbarPlacement && n.append(s), n.append($("<div>").addClass("row").append(r.addClass("col-md-6")).append(i.addClass("col-md-6"))), "bottom" === p.toolbarPlacement && n.append(s), n) : ("top" === p.toolbarPlacement && o.append(s), u() && o.append($("<li>").addClass(p.collapse && l() ? "collapse show" : "").append(r)), "default" === p.toolbarPlacement && o.append(s), l() && o.append($("<li>").addClass(p.collapse && u() ? "collapse" : "").append(i)), "bottom" === p.toolbarPlacement && o.append(s), n.append(o))
        }

        function n() {
            var e, t = (z || i).position(), a = (z || i).offset(), n = p.widgetPositioning.vertical,
                r = p.widgetPositioning.horizontal;
            if (p.widgetParent) e = p.widgetParent.append(N); else if (i.is("input")) e = i.after(N).parent(); else {
                if (p.inline) return void (e = i.append(N));
                (e = i).children().first().after(N)
            }
            if ("auto" === n && (n = a.top + 1.5 * N.height() >= $(window).height() + $(window).scrollTop() && N.height() + i.outerHeight() < a.top ? "top" : "bottom"), "auto" === r && (r = e.width() < a.left + N.outerWidth() / 2 && a.left + N.outerWidth() > $(window).width() ? "right" : "left"), "top" === n ? N.addClass("top").removeClass("bottom") : N.addClass("bottom").removeClass("top"), "right" === r ? N.addClass("pull-right") : N.removeClass("pull-right"), "static" === e.css("position") && (e = e.parents().filter(function () {
                return "static" !== $(this).css("position")
            }).first()), 0 === e.length) throw new Error("datetimepicker component should be placed within a non-static positioned container");
            N.css({
                top: "top" === n ? "auto" : t.top + i.outerHeight(),
                bottom: "top" === n ? e.outerHeight() - (e === i ? 0 : t.top) : "auto",
                left: "left" === r ? e === i ? 0 : t.left : "auto",
                right: "left" === r ? "auto" : e.outerWidth() - i.outerWidth() - (e === i ? 0 : t.left)
            })
        }

        function m(e) {
            "dp.change" === e.type && (e.date && e.date.isSame(e.oldDate) || !e.date && !e.oldDate) || i.trigger(e)
        }

        function r(e) {
            "y" === e && (e = "YYYY"), m({type: "dp.update", change: e, viewDate: H.clone()})
        }

        function o(e) {
            N && (e && (j = Math.max(V, Math.min(3, j + e))), N.find(".datepicker > div").hide().filter(".datepicker-" + Z[j].clsName).show())
        }

        function h(e, t) {
            var a, n, r, i;
            if (e.isValid() && !(p.disabledDates && "d" === t && (a = e, !0 === p.disabledDates[a.format("YYYY-MM-DD")]) || p.enabledDates && "d" === t && (n = e, !0 !== p.enabledDates[n.format("YYYY-MM-DD")]) || p.minDate && e.isBefore(p.minDate, t) || p.maxDate && e.isAfter(p.maxDate, t) || p.daysOfWeekDisabled && "d" === t && -1 !== p.daysOfWeekDisabled.indexOf(e.day()) || p.disabledHours && ("h" === t || "m" === t || "s" === t) && (r = e, !0 === p.disabledHours[r.format("H")]) || p.enabledHours && ("h" === t || "m" === t || "s" === t) && (i = e, !0 !== p.enabledHours[i.format("H")]))) {
                if (p.disabledTimeIntervals && ("h" === t || "m" === t || "s" === t)) {
                    var o = !1;
                    if ($.each(p.disabledTimeIntervals, function () {
                        if (e.isBetween(this[0], this[1])) return !(o = !0)
                    }), o) return
                }
                return 1
            }
        }

        function s() {
            var e, t, a, n, r, i, o = N.find(".datepicker-days"), s = o.find("th"), d = [], l = [];
            if (u()) {
                for (s.eq(0).find("span").attr("title", p.tooltips.prevMonth), s.eq(1).attr("title", p.tooltips.selectMonth), s.eq(2).find("span").attr("title", p.tooltips.nextMonth), o.find(".disabled").removeClass("disabled"), s.eq(1).text(H.format(p.dayViewHeaderFormat)), h(H.clone().subtract(1, "M"), "M") || s.eq(0).addClass("disabled"), h(H.clone().add(1, "M"), "M") || s.eq(2).addClass("disabled"), e = H.clone().startOf("M").startOf("w").startOf("d"), a = 0; a < 42; a++) 0 === e.weekday() && (t = $("<tr>"), p.calendarWeeks && t.append('<td class="cw">' + e.week() + "</td>"), d.push(t)), l = ["day"], e.isBefore(H, "M") && l.push("old"), e.isAfter(H, "M") && l.push("new"), e.isSame(E, "d") && !W && l.push("active"), h(e, "d") || l.push("disabled"), e.isSame(c(), "d") && l.push("today"), 0 !== e.day() && 6 !== e.day() || l.push("weekend"), m({
                    type: "dp.classify",
                    date: e,
                    classNames: l
                }), t.append('<td data-action="selectDay" data-day="' + e.format("L") + '" class="' + l.join(" ") + '">' + e.date() + "</td>"), e.add(1, "d");
                o.find("tbody").empty().append(d), n = N.find(".datepicker-months"), r = n.find("th"), i = n.find("tbody").find("span"), r.eq(0).find("span").attr("title", p.tooltips.prevYear), r.eq(1).attr("title", p.tooltips.selectYear), r.eq(2).find("span").attr("title", p.tooltips.nextYear), n.find(".disabled").removeClass("disabled"), h(H.clone().subtract(1, "y"), "y") || r.eq(0).addClass("disabled"), r.eq(1).text(H.year()), h(H.clone().add(1, "y"), "y") || r.eq(2).addClass("disabled"), i.removeClass("active"), E.isSame(H, "y") && !W && i.eq(E.month()).addClass("active"), i.each(function (e) {
                    h(H.clone().month(e), "M") || $(this).addClass("disabled")
                }), function () {
                    var e = N.find(".datepicker-years"), t = e.find("th"), a = H.clone().subtract(5, "y"),
                        n = H.clone().add(6, "y"), r = "";
                    for (t.eq(0).find("span").attr("title", p.tooltips.prevDecade), t.eq(1).attr("title", p.tooltips.selectDecade), t.eq(2).find("span").attr("title", p.tooltips.nextDecade), e.find(".disabled").removeClass("disabled"), p.minDate && p.minDate.isAfter(a, "y") && t.eq(0).addClass("disabled"), t.eq(1).text(a.year() + "-" + n.year()), p.maxDate && p.maxDate.isBefore(n, "y") && t.eq(2).addClass("disabled"); !a.isAfter(n, "y");) r += '<span data-action="selectYear" class="year' + (a.isSame(E, "y") && !W ? " active" : "") + (h(a, "y") ? "" : " disabled") + '">' + a.year() + "</span>", a.add(1, "y");
                    e.find("td").html(r)
                }(), function () {
                    var e, t = N.find(".datepicker-decades"), a = t.find("th"),
                        n = _({y: H.year() - H.year() % 100 - 1}), r = n.clone().add(100, "y"), i = n.clone(), o = !1,
                        s = !1, d = "";
                    for (a.eq(0).find("span").attr("title", p.tooltips.prevCentury), a.eq(2).find("span").attr("title", p.tooltips.nextCentury), t.find(".disabled").removeClass("disabled"), (n.isSame(_({y: 1900})) || p.minDate && p.minDate.isAfter(n, "y")) && a.eq(0).addClass("disabled"), a.eq(1).text(n.year() + "-" + r.year()), (n.isSame(_({y: 2e3})) || p.maxDate && p.maxDate.isBefore(r, "y")) && a.eq(2).addClass("disabled"); !n.isAfter(r, "y");) e = n.year() + 12, o = p.minDate && p.minDate.isAfter(n, "y") && p.minDate.year() <= e, s = p.maxDate && p.maxDate.isAfter(n, "y") && p.maxDate.year() <= e, d += '<span data-action="selectDecade" class="decade' + (E.isAfter(n) && E.year() <= e ? " active" : "") + (h(n, "y") || o || s ? "" : " disabled") + '" data-selection="' + (n.year() + 6) + '">' + (n.year() + 1) + " - " + (n.year() + 12) + "</span>", n.add(12, "y");
                    d += "<span></span><span></span><span></span>", t.find("td").html(d), a.eq(1).text(i.year() + 1 + "-" + n.year())
                }()
            }
        }

        function e() {
            var e, t, a = N.find(".timepicker span[data-time-component]");
            Y || (e = N.find(".timepicker [data-action=togglePeriod]"), t = E.clone().add(12 <= E.hours() ? -12 : 12, "h"), e.text(E.format("A")), h(t, "h") ? e.removeClass("disabled") : e.addClass("disabled")), a.filter("[data-time-component=hours]").text(E.format(Y ? "HH" : "hh")), a.filter("[data-time-component=minutes]").text(E.format("mm")), a.filter("[data-time-component=seconds]").text(E.format("ss")), function () {
                var e = N.find(".timepicker-hours table"), t = H.clone().startOf("d"), a = [], n = $("<tr>");
                for (11 < H.hour() && !Y && t.hour(12); t.isSame(H, "d") && (Y || H.hour() < 12 && t.hour() < 12 || 11 < H.hour());) t.hour() % 4 == 0 && (n = $("<tr>"), a.push(n)), n.append('<td data-action="selectHour" class="hour' + (h(t, "h") ? "" : " disabled") + '">' + t.format(Y ? "HH" : "hh") + "</td>"), t.add(1, "h");
                e.empty().append(a)
            }(), function () {
                for (var e = N.find(".timepicker-minutes table"), t = H.clone().startOf("h"), a = [], n = $("<tr>"), r = 1 === p.stepping ? 5 : p.stepping; H.isSame(t, "h");) t.minute() % (4 * r) == 0 && (n = $("<tr>"), a.push(n)), n.append('<td data-action="selectMinute" class="minute' + (h(t, "m") ? "" : " disabled") + '">' + t.format("mm") + "</td>"), t.add(r, "m");
                e.empty().append(a)
            }(), function () {
                for (var e = N.find(".timepicker-seconds table"), t = H.clone().startOf("m"), a = [], n = $("<tr>"); H.isSame(t, "m");) t.second() % 20 == 0 && (n = $("<tr>"), a.push(n)), n.append('<td data-action="selectSecond" class="second' + (h(t, "s") ? "" : " disabled") + '">' + t.format("ss") + "</td>"), t.add(5, "s");
                e.empty().append(a)
            }()
        }

        function y() {
            N && (s(), e())
        }

        function w(e) {
            var t = W ? null : E;
            if (!e) return W = !0, I.val(""), i.data("date", ""), m({
                type: "dp.change",
                date: !1,
                oldDate: t
            }), void y();
            if (e = e.clone().locale(p.locale), a() && e.tz(p.timeZone), 1 !== p.stepping) for (e.minutes(Math.round(e.minutes() / p.stepping) * p.stepping).seconds(0); p.minDate && e.isBefore(p.minDate);) e.add(p.stepping, "minutes");
            h(e) ? (H = (E = e).clone(), I.val(E.format(q)), i.data("date", E.format(q)), W = !1, y(), m({
                type: "dp.change",
                date: E.clone(),
                oldDate: t
            })) : (p.keepInvalid ? m({
                type: "dp.change",
                date: e,
                oldDate: t
            }) : I.val(W ? "" : E.format(q)), m({type: "dp.error", date: e, oldDate: t}))
        }

        function b() {
            var t = !1;
            return N ? (N.find(".collapse").each(function () {
                var e = $(this).data("collapse");
                return !e || !e.transitioning || !(t = !0)
            }), t || (z && z.hasClass("btn") && z.toggleClass("active"), N.hide(), $(window).off("resize", n), N.off("click", "[data-action]"), N.off("mousedown", !1), N.remove(), N = !1, m({
                type: "dp.hide",
                date: E.clone()
            }), I.blur(), H = E.clone()), L) : L
        }

        function g() {
            w(null)
        }

        function v(e) {
            return void 0 === p.parseInputDate ? (!_.isMoment(e) || e instanceof Date) && (e = c(e)) : e = p.parseInputDate(e), e
        }

        function k(e) {
            return $(e.currentTarget).is(".disabled") || X[$(e.currentTarget).data("action")].apply(L, arguments), !1
        }

        function D() {
            var e;
            return I.prop("disabled") || !p.ignoreReadonly && I.prop("readonly") || N || (void 0 !== I.val() && 0 !== I.val().trim().length ? w(v(I.val().trim())) : W && p.useCurrent && (p.inline || I.is("input") && 0 === I.val().trim().length) && (e = c(), "string" == typeof p.useCurrent && (e = {
                year: function (e) {
                    return e.month(0).date(1).hours(0).seconds(0).minutes(0)
                }, month: function (e) {
                    return e.date(1).hours(0).seconds(0).minutes(0)
                }, day: function (e) {
                    return e.hours(0).seconds(0).minutes(0)
                }, hour: function (e) {
                    return e.seconds(0).minutes(0)
                }, minute: function (e) {
                    return e.seconds(0)
                }
            }[p.useCurrent](e)), w(e)), N = t(), function () {
                var e = $("<tr>"), t = H.clone().startOf("w").startOf("d");
                for (!0 === p.calendarWeeks && e.append($("<th>").addClass("cw").text("#")); t.isBefore(H.clone().endOf("w"));) e.append($("<th>").addClass("dow").text(t.format("dd"))), t.add(1, "d");
                N.find(".datepicker-days thead").append(e)
            }(), function () {
                for (var e = [], t = H.clone().startOf("y").startOf("d"); t.isSame(H, "y");) e.push($("<span>").attr("data-action", "selectMonth").addClass("month").text(t.format("MMM"))), t.add(1, "M");
                N.find(".datepicker-months td").empty().append(e)
            }(), N.find(".timepicker-hours").hide(), N.find(".timepicker-minutes").hide(), N.find(".timepicker-seconds").hide(), y(), o(), $(window).on("resize", n), N.on("click", "[data-action]", k), N.on("mousedown", !1), z && z.hasClass("btn") && z.toggleClass("active"), n(), N.show(), p.focusOnShow && !I.is(":focus") && I.focus(), m({type: "dp.show"})), L
        }

        function C() {
            return (N ? b : D)()
        }

        function x(e) {
            var t, a, n, r, i = null, o = [], s = {}, d = e.which;
            for (t in K[d] = "p", K) K.hasOwnProperty(t) && "p" === K[t] && (o.push(t), parseInt(t, 10) !== d && (s[t] = !0));
            for (t in p.keyBinds) if (p.keyBinds.hasOwnProperty(t) && "function" == typeof p.keyBinds[t] && (n = t.split(" ")).length === o.length && J[d] === n[n.length - 1]) {
                for (r = !0, a = n.length - 2; 0 <= a; a--) if (!(J[n[a]] in s)) {
                    r = !1;
                    break
                }
                if (r) {
                    i = p.keyBinds[t];
                    break
                }
            }
            i && (i.call(L, N), e.stopPropagation(), e.preventDefault())
        }

        function T(e) {
            K[e.which] = "r", e.stopPropagation(), e.preventDefault()
        }

        function M(e) {
            var t = $(e.target).val().trim(), a = t ? v(t) : null;
            return w(a), e.stopImmediatePropagation(), !1
        }

        function S(e) {
            var t = {};
            return $.each(e, function () {
                var e = v(this);
                e.isValid() && (t[e.format("YYYY-MM-DD")] = !0)
            }), !!Object.keys(t).length && t
        }

        function O(e) {
            var t = {};
            return $.each(e, function () {
                t[this] = !0
            }), !!Object.keys(t).length && t
        }

        function P() {
            var e = p.format || "L LT";
            q = e.replace(/(\[[^\[]*\])|(\\)?(LTS|LT|LL?L?L?|l{1,4})/g, function (e) {
                return (E.localeData().longDateFormat(e) || e).replace(/(\[[^\[]*\])|(\\)?(LTS|LT|LL?L?L?|l{1,4})/g, function (e) {
                    return E.localeData().longDateFormat(e) || e
                })
            }), (B = p.extraFormats ? p.extraFormats.slice() : []).indexOf(e) < 0 && B.indexOf(q) < 0 && B.push(q), Y = q.toLowerCase().indexOf("a") < 1 && q.replace(/\[.*?\]/g, "").indexOf("h") < 1, d("y") && (V = 2), d("M") && (V = 1), d("d") && (V = 0), j = Math.max(V, j), W || w(E)
        }

        var E, H, I, Y, q, B, j, A, F, L = {}, W = !0, z = !1, N = !1, V = 0,
            Z = [{clsName: "days", navFnc: "M", navStep: 1}, {
                clsName: "months",
                navFnc: "y",
                navStep: 1
            }, {clsName: "years", navFnc: "y", navStep: 10}, {clsName: "decades", navFnc: "y", navStep: 100}],
            R = ["days", "months", "years", "decades"], Q = ["top", "bottom", "auto"], U = ["left", "right", "auto"],
            G = ["default", "top", "bottom"], J = {
                up: 38,
                38: "up",
                down: 40,
                40: "down",
                left: 37,
                37: "left",
                right: 39,
                39: "right",
                tab: 9,
                9: "tab",
                escape: 27,
                27: "escape",
                enter: 13,
                13: "enter",
                pageUp: 33,
                33: "pageUp",
                pageDown: 34,
                34: "pageDown",
                shift: 16,
                16: "shift",
                control: 17,
                17: "control",
                space: 32,
                32: "space",
                t: 84,
                84: "t",
                delete: 46,
                46: "delete"
            }, K = {}, X = {
                next: function () {
                    var e = Z[j].navFnc;
                    H.add(Z[j].navStep, e), s(), r(e)
                }, previous: function () {
                    var e = Z[j].navFnc;
                    H.subtract(Z[j].navStep, e), s(), r(e)
                }, pickerSwitch: function () {
                    o(1)
                }, selectMonth: function (e) {
                    var t = $(e.target).closest("tbody").find("span").index($(e.target));
                    H.month(t), j === V ? (w(E.clone().year(H.year()).month(H.month())), p.inline || b()) : (o(-1), s()), r("M")
                }, selectYear: function (e) {
                    var t = parseInt($(e.target).text(), 10) || 0;
                    H.year(t), j === V ? (w(E.clone().year(H.year())), p.inline || b()) : (o(-1), s()), r("YYYY")
                }, selectDecade: function (e) {
                    var t = parseInt($(e.target).data("selection"), 10) || 0;
                    H.year(t), j === V ? (w(E.clone().year(H.year())), p.inline || b()) : (o(-1), s()), r("YYYY")
                }, selectDay: function (e) {
                    var t = H.clone();
                    $(e.target).is(".old") && t.subtract(1, "M"), $(e.target).is(".new") && t.add(1, "M"), w(t.date(parseInt($(e.target).text(), 10))), l() || p.keepOpen || p.inline || b()
                }, incrementHours: function () {
                    var e = E.clone().add(1, "h");
                    h(e, "h") && w(e)
                }, incrementMinutes: function () {
                    var e = E.clone().add(p.stepping, "m");
                    h(e, "m") && w(e)
                }, incrementSeconds: function () {
                    var e = E.clone().add(1, "s");
                    h(e, "s") && w(e)
                }, decrementHours: function () {
                    var e = E.clone().subtract(1, "h");
                    h(e, "h") && w(e)
                }, decrementMinutes: function () {
                    var e = E.clone().subtract(p.stepping, "m");
                    h(e, "m") && w(e)
                }, decrementSeconds: function () {
                    var e = E.clone().subtract(1, "s");
                    h(e, "s") && w(e)
                }, togglePeriod: function () {
                    w(E.clone().add(12 <= E.hours() ? -12 : 12, "h"))
                }, togglePicker: function (e) {
                    var t, a = $(e.target), n = a.closest("a"), r = a.closest("ul"), i = r.find(".show"),
                        o = r.find(".collapse:not(.show)");
                    if (i && i.length) {
                        if ((t = i.data("collapse")) && t.transitioning) return;
                        i.collapse ? (i.collapse("hide"), o.collapse("show")) : (i.removeClass("show"), o.addClass("show")), a.is("i") ? a.toggleClass(p.icons.time + " " + p.icons.date) : a.find("i").toggleClass(p.icons.time + " " + p.icons.date), a.hasClass(p.icons.date) ? n.attr("title", p.tooltips.selectDate) : n.attr("title", p.tooltips.selectTime)
                    }
                }, showPicker: function () {
                    N.find(".timepicker > div:not(.timepicker-picker)").hide(), N.find(".timepicker .timepicker-picker").show()
                }, showHours: function () {
                    N.find(".timepicker .timepicker-picker").hide(), N.find(".timepicker .timepicker-hours").show()
                }, showMinutes: function () {
                    N.find(".timepicker .timepicker-picker").hide(), N.find(".timepicker .timepicker-minutes").show()
                }, showSeconds: function () {
                    N.find(".timepicker .timepicker-picker").hide(), N.find(".timepicker .timepicker-seconds").show()
                }, selectHour: function (e) {
                    var t = parseInt($(e.target).text(), 10);
                    Y || (12 <= E.hours() ? 12 !== t && (t += 12) : 12 === t && (t = 0)), w(E.clone().hours(t)), X.showPicker.call(L)
                }, selectMinute: function (e) {
                    w(E.clone().minutes(parseInt($(e.target).text(), 10))), X.showPicker.call(L)
                }, selectSecond: function (e) {
                    w(E.clone().seconds(parseInt($(e.target).text(), 10))), X.showPicker.call(L)
                }, clear: g, today: function () {
                    var e = c();
                    h(e, "d") && w(e)
                }, close: b
            };
        if (L.destroy = function () {
            b(), I.off({
                change: M,
                blur: blur,
                keydown: x,
                keyup: T,
                focus: p.allowInputToggle ? b : ""
            }), i.is("input") ? I.off({focus: D}) : z && (z.off("click", C), z.off("mousedown", !1)), i.removeData("DateTimePicker"), i.removeData("date")
        }, L.toggle = C, L.show = D, L.hide = b, L.disable = function () {
            return b(), z && z.hasClass("btn") && z.addClass("disabled"), I.prop("disabled", !0), L
        }, L.enable = function () {
            return z && z.hasClass("btn") && z.removeClass("disabled"), I.prop("disabled", !1), L
        }, L.ignoreReadonly = function (e) {
            if (0 === arguments.length) return p.ignoreReadonly;
            if ("boolean" != typeof e) throw new TypeError("ignoreReadonly () expects a boolean parameter");
            return p.ignoreReadonly = e, L
        }, L.options = function (e) {
            if (0 === arguments.length) return $.extend(!0, {}, p);
            if (!(e instanceof Object)) throw new TypeError("options() options parameter should be an object");
            return $.extend(!0, p, e), $.each(p, function (e, t) {
                if (void 0 === L[e]) throw new TypeError("option " + e + " is not recognized!");
                L[e](t)
            }), L
        }, L.date = function (e) {
            if (0 === arguments.length) return W ? null : E.clone();
            if (!(null === e || "string" == typeof e || _.isMoment(e) || e instanceof Date)) throw new TypeError("date() parameter must be one of [null, string, moment or Date]");
            return w(null === e ? null : v(e)), L
        }, L.format = function (e) {
            if (0 === arguments.length) return p.format;
            if ("string" != typeof e && ("boolean" != typeof e || !1 !== e)) throw new TypeError("format() expects a string or boolean:false parameter " + e);
            return p.format = e, q && P(), L
        }, L.timeZone = function (e) {
            if (0 === arguments.length) return p.timeZone;
            if ("string" != typeof e) throw new TypeError("newZone() expects a string parameter");
            return p.timeZone = e, L
        }, L.dayViewHeaderFormat = function (e) {
            if (0 === arguments.length) return p.dayViewHeaderFormat;
            if ("string" != typeof e) throw new TypeError("dayViewHeaderFormat() expects a string parameter");
            return p.dayViewHeaderFormat = e, L
        }, L.extraFormats = function (e) {
            if (0 === arguments.length) return p.extraFormats;
            if (!1 !== e && !(e instanceof Array)) throw new TypeError("extraFormats() expects an array or false parameter");
            return p.extraFormats = e, B && P(), L
        }, L.disabledDates = function (e) {
            if (0 === arguments.length) return p.disabledDates ? $.extend({}, p.disabledDates) : p.disabledDates;
            if (!e) return p.disabledDates = !1, y(), L;
            if (!(e instanceof Array)) throw new TypeError("disabledDates() expects an array parameter");
            return p.disabledDates = S(e), p.enabledDates = !1, y(), L
        }, L.enabledDates = function (e) {
            if (0 === arguments.length) return p.enabledDates ? $.extend({}, p.enabledDates) : p.enabledDates;
            if (!e) return p.enabledDates = !1, y(), L;
            if (!(e instanceof Array)) throw new TypeError("enabledDates() expects an array parameter");
            return p.enabledDates = S(e), p.disabledDates = !1, y(), L
        }, L.daysOfWeekDisabled = function (e) {
            if (0 === arguments.length) return p.daysOfWeekDisabled.splice(0);
            if ("boolean" == typeof e && !e) return p.daysOfWeekDisabled = !1, y(), L;
            if (!(e instanceof Array)) throw new TypeError("daysOfWeekDisabled() expects an array parameter");
            if (p.daysOfWeekDisabled = e.reduce(function (e, t) {
                return 6 < (t = parseInt(t, 10)) || t < 0 || isNaN(t) || -1 === e.indexOf(t) && e.push(t), e
            }, []).sort(), p.useCurrent && !p.keepInvalid) {
                for (var t = 0; !h(E, "d");) {
                    if (E.add(1, "d"), 31 === t) throw"Tried 31 times to find a valid date";
                    t++
                }
                w(E)
            }
            return y(), L
        }, L.maxDate = function (e) {
            if (0 === arguments.length) return p.maxDate ? p.maxDate.clone() : p.maxDate;
            if ("boolean" == typeof e && !1 === e) return p.maxDate = !1, y(), L;
            "string" == typeof e && ("now" !== e && "moment" !== e || (e = c()));
            var t = v(e);
            if (!t.isValid()) throw new TypeError("maxDate() Could not parse date parameter: " + e);
            if (p.minDate && t.isBefore(p.minDate)) throw new TypeError("maxDate() date parameter is before options.minDate: " + t.format(q));
            return p.maxDate = t, p.useCurrent && !p.keepInvalid && E.isAfter(e) && w(p.maxDate), H.isAfter(t) && (H = t.clone().subtract(p.stepping, "m")), y(), L
        }, L.minDate = function (e) {
            if (0 === arguments.length) return p.minDate ? p.minDate.clone() : p.minDate;
            if ("boolean" == typeof e && !1 === e) return p.minDate = !1, y(), L;
            "string" == typeof e && ("now" !== e && "moment" !== e || (e = c()));
            var t = v(e);
            if (!t.isValid()) throw new TypeError("minDate() Could not parse date parameter: " + e);
            if (p.maxDate && t.isAfter(p.maxDate)) throw new TypeError("minDate() date parameter is after options.maxDate: " + t.format(q));
            return p.minDate = t, p.useCurrent && !p.keepInvalid && E.isBefore(e) && w(p.minDate), H.isBefore(t) && (H = t.clone().add(p.stepping, "m")), y(), L
        }, L.defaultDate = function (e) {
            if (0 === arguments.length) return p.defaultDate ? p.defaultDate.clone() : p.defaultDate;
            if (!e) return p.defaultDate = !1, L;
            "string" == typeof e && (e = "now" === e || "moment" === e ? c() : c(e));
            var t = v(e);
            if (!t.isValid()) throw new TypeError("defaultDate() Could not parse date parameter: " + e);
            if (!h(t)) throw new TypeError("defaultDate() date passed is invalid according to component setup validations");
            return p.defaultDate = t, (p.defaultDate && p.inline || "" === I.val().trim()) && w(p.defaultDate), L
        }, L.locale = function (e) {
            if (0 === arguments.length) return p.locale;
            if (!_.localeData(e)) throw new TypeError("locale() locale " + e + " is not loaded from moment locales!");
            return p.locale = e, E.locale(p.locale), H.locale(p.locale), q && P(), N && (b(), D()), L
        }, L.stepping = function (e) {
            return 0 === arguments.length ? p.stepping : (e = parseInt(e, 10), (isNaN(e) || e < 1) && (e = 1), p.stepping = e, L)
        }, L.useCurrent = function (e) {
            var t = ["year", "month", "day", "hour", "minute"];
            if (0 === arguments.length) return p.useCurrent;
            if ("boolean" != typeof e && "string" != typeof e) throw new TypeError("useCurrent() expects a boolean or string parameter");
            if ("string" == typeof e && -1 === t.indexOf(e.toLowerCase())) throw new TypeError("useCurrent() expects a string parameter of " + t.join(", "));
            return p.useCurrent = e, L
        }, L.collapse = function (e) {
            if (0 === arguments.length) return p.collapse;
            if ("boolean" != typeof e) throw new TypeError("collapse() expects a boolean parameter");
            return p.collapse === e || (p.collapse = e, N && (b(), D())), L
        }, L.icons = function (e) {
            if (0 === arguments.length) return $.extend({}, p.icons);
            if (!(e instanceof Object)) throw new TypeError("icons() expects parameter to be an Object");
            return $.extend(p.icons, e), N && (b(), D()), L
        }, L.tooltips = function (e) {
            if (0 === arguments.length) return $.extend({}, p.tooltips);
            if (!(e instanceof Object)) throw new TypeError("tooltips() expects parameter to be an Object");
            return $.extend(p.tooltips, e), N && (b(), D()), L
        }, L.useStrict = function (e) {
            if (0 === arguments.length) return p.useStrict;
            if ("boolean" != typeof e) throw new TypeError("useStrict() expects a boolean parameter");
            return p.useStrict = e, L
        }, L.sideBySide = function (e) {
            if (0 === arguments.length) return p.sideBySide;
            if ("boolean" != typeof e) throw new TypeError("sideBySide() expects a boolean parameter");
            return p.sideBySide = e, N && (b(), D()), L
        }, L.viewMode = function (e) {
            if (0 === arguments.length) return p.viewMode;
            if ("string" != typeof e) throw new TypeError("viewMode() expects a string parameter");
            if (-1 === R.indexOf(e)) throw new TypeError("viewMode() parameter must be one of (" + R.join(", ") + ") value");
            return p.viewMode = e, j = Math.max(R.indexOf(e), V), o(), L
        }, L.toolbarPlacement = function (e) {
            if (0 === arguments.length) return p.toolbarPlacement;
            if ("string" != typeof e) throw new TypeError("toolbarPlacement() expects a string parameter");
            if (-1 === G.indexOf(e)) throw new TypeError("toolbarPlacement() parameter must be one of (" + G.join(", ") + ") value");
            return p.toolbarPlacement = e, N && (b(), D()), L
        }, L.widgetPositioning = function (e) {
            if (0 === arguments.length) return $.extend({}, p.widgetPositioning);
            if ("[object Object]" !== {}.toString.call(e)) throw new TypeError("widgetPositioning() expects an object variable");
            if (e.horizontal) {
                if ("string" != typeof e.horizontal) throw new TypeError("widgetPositioning() horizontal variable must be a string");
                if (e.horizontal = e.horizontal.toLowerCase(), -1 === U.indexOf(e.horizontal)) throw new TypeError("widgetPositioning() expects horizontal parameter to be one of (" + U.join(", ") + ")");
                p.widgetPositioning.horizontal = e.horizontal
            }
            if (e.vertical) {
                if ("string" != typeof e.vertical) throw new TypeError("widgetPositioning() vertical variable must be a string");
                if (e.vertical = e.vertical.toLowerCase(), -1 === Q.indexOf(e.vertical)) throw new TypeError("widgetPositioning() expects vertical parameter to be one of (" + Q.join(", ") + ")");
                p.widgetPositioning.vertical = e.vertical
            }
            return y(), L
        }, L.calendarWeeks = function (e) {
            if (0 === arguments.length) return p.calendarWeeks;
            if ("boolean" != typeof e) throw new TypeError("calendarWeeks() expects parameter to be a boolean value");
            return p.calendarWeeks = e, y(), L
        }, L.showTodayButton = function (e) {
            if (0 === arguments.length) return p.showTodayButton;
            if ("boolean" != typeof e) throw new TypeError("showTodayButton() expects a boolean parameter");
            return p.showTodayButton = e, N && (b(), D()), L
        }, L.showClear = function (e) {
            if (0 === arguments.length) return p.showClear;
            if ("boolean" != typeof e) throw new TypeError("showClear() expects a boolean parameter");
            return p.showClear = e, N && (b(), D()), L
        }, L.widgetParent = function (e) {
            if (0 === arguments.length) return p.widgetParent;
            if ("string" == typeof e && (e = $(e)), null !== e && "string" != typeof e && !(e instanceof $)) throw new TypeError("widgetParent() expects a string or a jQuery object parameter");
            return p.widgetParent = e, N && (b(), D()), L
        }, L.keepOpen = function (e) {
            if (0 === arguments.length) return p.keepOpen;
            if ("boolean" != typeof e) throw new TypeError("keepOpen() expects a boolean parameter");
            return p.keepOpen = e, L
        }, L.focusOnShow = function (e) {
            if (0 === arguments.length) return p.focusOnShow;
            if ("boolean" != typeof e) throw new TypeError("focusOnShow() expects a boolean parameter");
            return p.focusOnShow = e, L
        }, L.inline = function (e) {
            if (0 === arguments.length) return p.inline;
            if ("boolean" != typeof e) throw new TypeError("inline() expects a boolean parameter");
            return p.inline = e, L
        }, L.clear = function () {
            return g(), L
        }, L.keyBinds = function (e) {
            return 0 === arguments.length ? p.keyBinds : (p.keyBinds = e, L)
        }, L.getMoment = c, L.debug = function (e) {
            if ("boolean" != typeof e) throw new TypeError("debug() expects a boolean parameter");
            return p.debug = e, L
        }, L.allowInputToggle = function (e) {
            if (0 === arguments.length) return p.allowInputToggle;
            if ("boolean" != typeof e) throw new TypeError("allowInputToggle() expects a boolean parameter");
            return p.allowInputToggle = e, L
        }, L.showClose = function (e) {
            if (0 === arguments.length) return p.showClose;
            if ("boolean" != typeof e) throw new TypeError("showClose() expects a boolean parameter");
            return p.showClose = e, L
        }, L.keepInvalid = function (e) {
            if (0 === arguments.length) return p.keepInvalid;
            if ("boolean" != typeof e) throw new TypeError("keepInvalid() expects a boolean parameter");
            return p.keepInvalid = e, L
        }, L.datepickerInput = function (e) {
            if (0 === arguments.length) return p.datepickerInput;
            if ("string" != typeof e) throw new TypeError("datepickerInput() expects a string parameter");
            return p.datepickerInput = e, L
        }, L.parseInputDate = function (e) {
            if (0 === arguments.length) return p.parseInputDate;
            if ("function" != typeof e) throw new TypeError("parseInputDate() sholud be as function");
            return p.parseInputDate = e, L
        }, L.disabledTimeIntervals = function (e) {
            if (0 === arguments.length) return p.disabledTimeIntervals ? $.extend({}, p.disabledTimeIntervals) : p.disabledTimeIntervals;
            if (!e) return p.disabledTimeIntervals = !1, y(), L;
            if (!(e instanceof Array)) throw new TypeError("disabledTimeIntervals() expects an array parameter");
            return p.disabledTimeIntervals = e, y(), L
        }, L.disabledHours = function (e) {
            if (0 === arguments.length) return p.disabledHours ? $.extend({}, p.disabledHours) : p.disabledHours;
            if (!e) return p.disabledHours = !1, y(), L;
            if (!(e instanceof Array)) throw new TypeError("disabledHours() expects an array parameter");
            if (p.disabledHours = O(e), p.enabledHours = !1, p.useCurrent && !p.keepInvalid) {
                for (var t = 0; !h(E, "h");) {
                    if (E.add(1, "h"), 24 === t) throw"Tried 24 times to find a valid date";
                    t++
                }
                w(E)
            }
            return y(), L
        }, L.enabledHours = function (e) {
            if (0 === arguments.length) return p.enabledHours ? $.extend({}, p.enabledHours) : p.enabledHours;
            if (!e) return p.enabledHours = !1, y(), L;
            if (!(e instanceof Array)) throw new TypeError("enabledHours() expects an array parameter");
            if (p.enabledHours = O(e), p.disabledHours = !1, p.useCurrent && !p.keepInvalid) {
                for (var t = 0; !h(E, "h");) {
                    if (E.add(1, "h"), 24 === t) throw"Tried 24 times to find a valid date";
                    t++
                }
                w(E)
            }
            return y(), L
        }, L.viewDate = function (e) {
            if (0 === arguments.length) return H.clone();
            if (!e) return H = E.clone(), L;
            if (!("string" == typeof e || _.isMoment(e) || e instanceof Date)) throw new TypeError("viewDate() parameter must be one of [string, moment or Date]");
            return H = v(e), r(), L
        }, i.is("input")) I = i; else if (0 === (I = i.find(p.datepickerInput)).length) I = i.find("input"); else if (!I.is("input")) throw new Error('CSS class "' + p.datepickerInput + '" cannot be applied to non input element');
        if (i.hasClass("input-group") && (0 === i.find(".datepickerbutton").length ? 0 === (z = i.find(".input-group-text")).length && (z = i.find(".input-group-addon")) : z = i.find(".datepickerbutton")), !p.inline && !I.is("input")) throw new Error("Could not initialize DateTimePicker without an input element");
        return E = c(), H = E.clone(), $.extend(!0, p, (A = {}, (F = i.is("input") || p.inline ? i.data() : i.find("input").data()).dateOptions && F.dateOptions instanceof Object && (A = $.extend(!0, A, F.dateOptions)), $.each(p, function (e) {
            var t = "date" + e.charAt(0).toUpperCase() + e.slice(1);
            void 0 !== F[t] && (A[e] = F[t])
        }), A)), L.options(p), P(), I.on({
            change: M,
            blur: p.debug ? "" : b,
            keydown: x,
            keyup: T,
            focus: p.allowInputToggle ? D : ""
        }), i.is("input") ? I.on({focus: D}) : z && (z.on("click", C), z.on("mousedown", !1)), I.prop("disabled") && L.disable(), I.is("input") && 0 !== I.val().trim().length ? w(v(I.val().trim())) : p.defaultDate && void 0 === I.attr("placeholder") && w(p.defaultDate), p.inline && D(), L
    }

    return $.fn.datetimepicker = function (a) {
        a = a || {};
        var t, n = Array.prototype.slice.call(arguments, 1), r = !0;
        if ("object" == typeof a) return this.each(function () {
            var e, t = $(this);
            t.data("DateTimePicker") || (e = $.extend(!0, {}, $.fn.datetimepicker.defaults, a), t.data("DateTimePicker", i(t, e)))
        });
        if ("string" == typeof a) return this.each(function () {
            var e = $(this).data("DateTimePicker");
            if (!e) throw new Error('bootstrap-datetimepicker("' + a + '") method was called on an element that is not using DateTimePicker');
            t = e[a].apply(e, n), r = t === e
        }), r || -1 < $.inArray(a, ["destroy", "hide", "show", "toggle"]) ? this : t;
        throw new TypeError("Invalid arguments for DateTimePicker: " + a)
    }, $.fn.datetimepicker.defaults = {
        timeZone: "",
        format: !1,
        dayViewHeaderFormat: "MMMM YYYY",
        extraFormats: !1,
        stepping: 1,
        minDate: !1,
        maxDate: !1,
        useCurrent: !0,
        collapse: !0,
        locale: _.locale(),
        defaultDate: !1,
        disabledDates: !1,
        enabledDates: !1,
        icons: {
            time: "fa fa-clock-o",
            date: "fa fa-calendar",
            up: "fa fa-chevron-up",
            down: "fa fa-chevron-down",
            previous: "fa fa-chevron-left",
            next: "fa fa-chevron-right",
            today: "fa fa-crosshairs",
            clear: "fa fa-trash-o",
            close: "fa fa-times"
        },
        tooltips: {
            today: "Go to today",
            clear: "Clear selection",
            close: "Close the picker",
            selectMonth: "Select Month",
            prevMonth: "Previous Month",
            nextMonth: "Next Month",
            selectYear: "Select Year",
            prevYear: "Previous Year",
            nextYear: "Next Year",
            selectDecade: "Select Decade",
            prevDecade: "Previous Decade",
            nextDecade: "Next Decade",
            prevCentury: "Previous Century",
            nextCentury: "Next Century",
            pickHour: "Pick Hour",
            incrementHour: "Increment Hour",
            decrementHour: "Decrement Hour",
            pickMinute: "Pick Minute",
            incrementMinute: "Increment Minute",
            decrementMinute: "Decrement Minute",
            pickSecond: "Pick Second",
            incrementSecond: "Increment Second",
            decrementSecond: "Decrement Second",
            togglePeriod: "Toggle Period",
            selectTime: "Select Time",
            selectDate: "Select Date"
        },
        useStrict: !1,
        sideBySide: !1,
        daysOfWeekDisabled: !1,
        calendarWeeks: !1,
        viewMode: "days",
        toolbarPlacement: "default",
        showTodayButton: !1,
        showClear: !1,
        showClose: !1,
        widgetPositioning: {horizontal: "auto", vertical: "auto"},
        widgetParent: null,
        ignoreReadonly: !1,
        keepOpen: !1,
        focusOnShow: !0,
        inline: !1,
        keepInvalid: !1,
        datepickerInput: ".datepickerinput",
        keyBinds: {
            up: function (e) {
                var t;
                e && (t = this.date() || this.getMoment(), e.find(".datepicker").is(":visible") ? this.date(t.clone().subtract(7, "d")) : this.date(t.clone().add(this.stepping(), "m")))
            }, down: function (e) {
                var t;
                e ? (t = this.date() || this.getMoment(), e.find(".datepicker").is(":visible") ? this.date(t.clone().add(7, "d")) : this.date(t.clone().subtract(this.stepping(), "m"))) : this.show()
            }, "control up": function (e) {
                var t;
                e && (t = this.date() || this.getMoment(), e.find(".datepicker").is(":visible") ? this.date(t.clone().subtract(1, "y")) : this.date(t.clone().add(1, "h")))
            }, "control down": function (e) {
                var t;
                e && (t = this.date() || this.getMoment(), e.find(".datepicker").is(":visible") ? this.date(t.clone().add(1, "y")) : this.date(t.clone().subtract(1, "h")))
            }, left: function (e) {
                var t;
                e && (t = this.date() || this.getMoment(), e.find(".datepicker").is(":visible") && this.date(t.clone().subtract(1, "d")))
            }, right: function (e) {
                var t;
                e && (t = this.date() || this.getMoment(), e.find(".datepicker").is(":visible") && this.date(t.clone().add(1, "d")))
            }, pageUp: function (e) {
                var t;
                e && (t = this.date() || this.getMoment(), e.find(".datepicker").is(":visible") && this.date(t.clone().subtract(1, "M")))
            }, pageDown: function (e) {
                var t;
                e && (t = this.date() || this.getMoment(), e.find(".datepicker").is(":visible") && this.date(t.clone().add(1, "M")))
            }, enter: function () {
                this.hide()
            }, escape: function () {
                this.hide()
            }, "control space": function (e) {
                e && e.find(".timepicker").is(":visible") && e.find('.btn[data-action="togglePeriod"]').click()
            }, t: function () {
                this.date(this.getMoment())
            }, delete: function () {
                this.clear()
            }
        },
        debug: !1,
        allowInputToggle: !1,
        disabledTimeIntervals: !1,
        disabledHours: !1,
        enabledHours: !1,
        viewDate: !1
    }, $.fn.datetimepicker
});