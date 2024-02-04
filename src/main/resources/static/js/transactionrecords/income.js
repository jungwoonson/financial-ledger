class CustomTextEditor {
    constructor(props) {
        const el = document.createElement('input');

        el.type = 'text';
        el.value = String(!!!props.value ? '0' : props.value);

        this.el = el;
    }

    getElement() {
        return this.el;
    }

    getValue() {
        return this.el.value
    }

    mounted() {
        this.el.select();
    }
}

const grid = new tui.Grid({
    el: document.getElementById('grid'),
    data: [], // 여기에 데이터를 채웁니다
    scrollX: false,
    scrollY: false,
    rowHeaders: ['rowNum'],
    columns: [
        {
            header: '날짜',
            name: 'date',
            editor: 'datePicker',
        },
        {
            header: '성명(입금인)',
            name: 'person_name',
            editor: CustomTextEditor,
            validation: {
                regExp: /^[1-9][0-9]*$/
            },
            onBeforeChange(ev) {
                console.log('Before change:' + ev.value);
            },
            onAfterChange(ev) {
                console.log(ev);
                console.log('After change:' + ev.value);
            },
        },
        {
            header: '금액',
            name: 'amount',
            editor: 'text'
        },
        {
            header: '내용',
            name: 'details',
            editor: 'text'
        },
        {
            header: '구분',
            name: 'category',
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