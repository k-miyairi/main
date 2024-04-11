function displaySelected() {
    var selectedMemoId = document.querySelector('input[name="selectedMemo"]:checked').value;
    document.getElementById('displayForm').querySelector('input[name="id"]').value = selectedMemoId;
    document.getElementById('displayForm').submit();
}

function deleteSelected() {
    var selectedMemoId = document.querySelector('input[name="selectedMemo"]:checked').value;
    if (confirm('本当に削除しますか？')) {
        document.getElementById('deleteForm').querySelector('input[name="id"]').value = selectedMemoId;
        document.getElementById('deleteForm').submit();
    }
}

function selectRow(row) {
    // ラジオボタンを取得し、選択状態にする
    var radio = row.querySelector('input[type="radio"]');
    radio.checked = true;
}