const grid = new tui.Grid({
    el: document.getElementById('grid'),
    data: [], // 여기에 데이터를 채웁니다
    scrollX: false,
    scrollY: false,
    rowHeaders: ['rowNum'],
    columns: [
        {
            header: '주',
            name: 'week',
            editor: 'text'
        },
        {
            header: '기부금',
            name: 'donations',
            editor: {
                type: 'datePicker',
            },
        },
        {
            header: '판매',
            name: 'sales',
            editor: 'text'
        },
        {
            header: '총 수입',
            name: 'totalIncome',
            editor: 'text'
        }
    ],
    summary: {
        height: 40,
        position: 'bottom',
        columnContent: {
            sales: {
                template: function(valueMap) {
                    return `합계: ${valueMap.sum} 원`;
                }
            },
            totalIncome: {
                template: function(valueMap) {
                    return `합계: ${valueMap.sum} 원`;
                }
            }
        }
    }
});

// 그리드에 데이터를 로드하는 함수
function loadData(data) {
    grid.resetData(data);
}

// 예제 데이터
const weeklyIncomeData = [
    {week: '1주', donations: '1995-07-21', sales: 500, totalIncome: 1500},
    {week: '2주', donations: '1995-07-21', sales: 550, totalIncome: 1650},
    // 필요에 따라 더 많은 데이터를 추가
];

// 그리드에 데이터 로드
loadData(weeklyIncomeData);

grid.appendRow();
grid.appendRow();
grid.appendRow();
grid.appendRow();
grid.appendRow();
grid.appendRow();
grid.appendRow();
grid.appendRow();