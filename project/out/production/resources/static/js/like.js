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

    console.log(boardId);
    $(".content").on("click", function () {
        $.ajax({
            url: '/boards/like',
            type: 'POST',
            data: {'boardId': boardId},
            success: function (data) {
                if (data.result == 1) {
                    $(".like").addClass("heart-active");
                    $(".heart").addClass("heart-active");
                } else {
                    $(".like").removeClass("heart-active");
                    $(".heart").removeClass("heart-active");
                }
                $("#likeCount").text('like '+ data.count);
            },
            error: function () {
            }
        });
    });
});