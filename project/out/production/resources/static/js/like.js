$(document).ready(function () {
    let checkLike = $("#checkLike").attr("value");
    console.log(checkLike);
    if (checkLike == "is") {
        $(".like").addClass("heart-active");
        $(".heart").addClass("heart-active");
    } else if (checkLike == "not") {
        $(".like").removeClass("heart-active");
        $(".heart").removeClass("heart-active");
    }
    const boardId = $("#boardId").attr("value");
    const likeCount = $("#likeCount").attr("text");

    console.log(boardId);
    $(".content").on("click", function () {
        $.ajax({
            url: '/boards/like',
            async: false,
            type: 'POST',
            data: {'boardId': boardId},
            success: function (data) {
                if (data == 1) {
                    $(".like").addClass("heart-active");
                    $(".heart").addClass("heart-active");
                } else {
                    $(".like").removeClass("heart-active");
                    $(".heart").removeClass("heart-active");
                }
            },
            error: function () {
            }
        });
        $.ajax({
            url: '/boards/likeCount',
            type: 'POST',
            data: {'boardId': boardId},
            success: function (data) {
               $("#likeCount").text(data);
            },
            error: function () {
            }
        });
    });
});