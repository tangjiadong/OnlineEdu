'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});
//es6第二种方法，也是最常用方法
//在js中写export default，在这个对象中写方法名字
exports.default = {
    getList: function getList() {
        console.log('获取数据列表');
    },
    save: function save() {
        console.log('保存数据');
    }
};