console.log(data);
// canvasの取得
const ctx = document.getElementById('graph').getContext('2d');

// 日付とボリュームを配列に分ける
const labels = data.map(d => `${d.logDate.year}-${d.logDate.monthValue}-${d.logDate.dayOfMonth}`);
const volumes = data.map(d => d.volume);

// グラフの描画
new Chart(ctx, {
    type: 'line',
    data: {
        labels: labels,
        datasets: [{
            label: 'トレーニングボリューム',
            data: volumes,
            borderColor: 'rgb(75, 192, 192)',
            tension: 0.1
        }]
    }
});

// プルダウン変更時に自動でフォームをsubmit
document.getElementById('exerciseId').addEventListener('change', function() {
    this.closest('form').submit();
});