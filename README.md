# user_msg_manager
用户信息管理系统，采用servlet+mysql+jdbc技术和借助springMVC开发模式
## 需求分析
1. 简单功能
	1. 列表查询
	2. 登录
	3. 注册
	4. 添加用户
	5. 删除用户
	6. 修改用户
2. 复杂功能
	1. 删除选中
	2. 分页查询
	3. 复杂条件查询
## 系统实现

v2:添加了拦截器功能
v3:检验用户名是否存在
使用了ajax+json向后台写数据
注意点：服务器响应数据时，客户端若想当作jason数据使用，则有两个方法：
1. 在ajax客户端请求时就应该设置type为“json”
2. 在服务器端设置响应的mime类型为response.setContentType("application/json;charset=utf-8")
