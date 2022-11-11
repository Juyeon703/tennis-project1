$(document).ready(function () {
    $("#reportBtn").click(function () {

        Swal.fire({
            title: '신고 내용을 작성해주세요',
            input: 'text',
            inputAttributes: {
                maxLength: 20,
                required: true
            },
            inputValidator: (value) => {
                if (!value) {
                    return 'You need to write something!'
                } else if (value.length < 6) {
                    return '5자 이상 입력해주세요'
                }
            },
            showCancelButton: true,
            confirmButtonText: '신고하기',
        }).then(function (result) {
            if (result.isConfirmed) {
                    const boardId = $("#boardId").attr("value");
                console.log(result.value)
                $.ajax({
                    type: "POST",
                    url: "/boards/report",
                    data: {'boardId': boardId, 'content': result.value},
                    success: function (data) {
                        if (data === 1) {
                            Swal.fire({
                                icon: 'info',
                                title: '이미 신고된 게시글입니다.'
                            })
                        } else {
                            Swal.fire({
                                icon: 'success',
                                title: '신고되었습니다'
                            })
                        }
                    },
                    error: function (data) {
                        Swal.fire({
                            icon: 'error',
                            title: '오류가 발생했습니다'
                        })
                    }
                });
            }
        })
    });
});