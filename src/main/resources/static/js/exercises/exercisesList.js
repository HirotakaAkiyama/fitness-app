document.querySelectorAll('input[type="radio"]').forEach(function(radio) {
    radio.addEventListener('change', function() {
        // 選択した種目のIDをhiddenフィールドにセット
        document.getElementById('selected-id').value = this.dataset.id;
        // フォームを送信
        document.getElementById('default-form').submit();
    });
});
