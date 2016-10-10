$(function() {
                $('#chineseName').autocomplete(emails, {
                    max: 12,    //列表里的条目数
                    minChars: 0,    //自动完成激活之前填入的最小字符
                    width: 400,     //提示的宽度，溢出隐藏
                    scrollHeight: 300,   //提示的高度，溢出显示滚动条
                    matchContains: true,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
                     autoFill: false,    //自动填充
                     formatItem: function(row, i, max) {
                         return i + '/' + max + ':"' + row.name + '"[' + row.to + ']';
                     },
                    formatMatch: function(row, i, max) {
                        return row.name + row.to;
                     },
                    formatResult: function(row) {
                         return row.to;
                     }
                 }).result(function(event, row, formatted) {
                     alert(row.to);
                 });
            });