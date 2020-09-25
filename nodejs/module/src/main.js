//调用common.js
//引入模块，注意：当前路径必须写 ./
const m = require('./common.js')
console.log(m)

const result1 = m.sum(1,2)
const reuslt2 = m.subtract(1,2)
console.log(result1,reuslt2)