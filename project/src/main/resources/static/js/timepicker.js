let startDate;
let endDate;

$('#matchingDate').daterangepicker({
    "timePicker": true,
    "timePicker24Hour": true,
    "timePickerIncrement": 10,
    locale: {
        format: 'M/DD hh:mm'
    }
}, function (start, end, label) {
    console.log('New date range selected: ' + start.format('YYMMDDHHmm') + ' to ' + end.format('YYMMDDHHmm') + ' (predefined range: ' + label + ')');
    console.log(start._d)
    console.log(end._d)
    startDate = start._d;
    endDate = end._d;

    // 스트링 date로 변환?
//     const startTime = new Date(parseInt(start.format('YYYY')),
//         parseInt(start.format('MM')),
//         parseInt(start.format('DD')),
//         parseInt(start.format('HH')),
//         parseInt(start.format('mm')));
//     console.log(startTime);
//
// date = startTime
//     date.setHours(startTime.getHours() - 1); // 시간계산 예제 한시간 전
//     console.log(date);
//
//     const endTime = new Date(parseInt(end.format('YYYY')),
//         parseInt(end.format('MM')),
//         parseInt(end.format('DD')),
//         parseInt(end.format('HH')),
//         parseInt(end.format('mm')));
//     console.log(endTime);
});


$(document).ready(function () {

    $("#submit_btn").click(function () {
        console.log(startDate);
        console.log(endDate);

        let matchingForm = {
            title: $("#matchingTitle").val(),
            startDate: startDate,
            endDate: endDate,
            place: $("#matchingPlace").val(),
            matchingType: $("#matchingType2").val(),
            courtType: $("#courtType2").val(),
        }
        console.log(matchingForm);

        $.ajax({
            type: "post",
            url: "/matching/new",
            data: $('#matchingTitle').val(),
            success: function (response) {
                alert("성공!")
            },
            error: function (response) {
                alert("서버 에러!");
            },
        });
    });


});