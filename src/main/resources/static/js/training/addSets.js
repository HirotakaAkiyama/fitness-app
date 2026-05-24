// セット追加・削除の管理
const container = document.getElementById('sets-container');
const addBtn = document.getElementById('add-set-btn');

// 現在のセット数を返す
function getSetCount() {
    return container.querySelectorAll('.set-section').length;
}

// 指定したインデックスでセクションを作る
function createSetSection(index) {
    const section = document.createElement('div');
    section.className = 'set-section';

    section.innerHTML = `
        <div class="set-row">
            <label>セット${index + 1}</label>
            <label>重量</label>
            <input type="number" name="sets[${index}].weightKg" />
            <label>回数</label>
            <input type="number" name="sets[${index}].reps" />
            <button type="button" class="btn btn-danger remove-set-btn">削除</button>
        </div>
    `;

    // 削除ボタンのイベントを登録
    section.querySelector('.remove-set-btn').addEventListener('click', function () {
        section.remove();
        reIndex();
    });

    return section;
}

// 全セクションのインデックスを振り直す
function reIndex() {
    const sections = container.querySelectorAll('.set-section');
    sections.forEach((section, i) => {
        // ラベルのセット番号を更新
        section.querySelector('.set-row label:first-child').textContent = `セット${i + 1}`;

        // inputのname属性を更新
        section.querySelector('input[name*="weightKg"]').name = `sets[${i}].weightKg`;
        section.querySelector('input[name*="reps"]').name = `sets[${i}].reps`;
    });
}

// セット追加ボタン
addBtn.addEventListener('click', function () {
    const index = getSetCount();
    const section = createSetSection(index);
    container.appendChild(section);
});

// 最初からあるセット1にも削除ボタンを付ける
(function initFirstSet() {
    const firstSection = container.querySelector('.set-section');
    const deleteBtn = document.createElement('button');
    deleteBtn.type = 'button';
    deleteBtn.className = 'btn btn-danger remove-set-btn';
    deleteBtn.textContent = '削除';
    deleteBtn.addEventListener('click', function () {
        firstSection.remove();
        reIndex();
    });
    firstSection.querySelector('.set-row').appendChild(deleteBtn);
})();
