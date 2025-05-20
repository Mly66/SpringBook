import request from '../axios/request'; 

// 获取用户分页数据
export const fetchUsers = (page, userName, userSex) => {
  const postData = new URLSearchParams({
    userName,
    userSex,
    page,
  }).toString();

  return request.post('/user/selectUserPage', postData, { withCredentials: true });
};

// 获取用户数量
export const fetchUserCount = (userName, userSex) => {
  const postData = new URLSearchParams({
    userName,
    userSex,
  }).toString();

  return request.post('/user/getRowCount', postData, { withCredentials: true });
};

// 创建用户
export const createUser = (user) => {
  return request.post('/user/createUser', user, { withCredentials: true });
};

// 根据ID删除用户
export const deleteUserById = (userId) => {
  const postData = new URLSearchParams({ userId }).toString();

  return request.post('/user/deleteUserById', postData, { withCredentials: true });
};

// 根据ID批量删除用户
export const deleteUserByIdList = (userIdList) => {
  return request.post(
    '/user/deleteUserByIdList',
    userIdList,
    {
      headers: { 'Content-Type': 'application/json' },
      withCredentials: true,
    }
  );
};

// 根据用户ID获取个人信息
export const fetchUserById = (userId) => {
  return request.get(`/user/fetchUserById`, {
    params: { userId }, 
    withCredentials: true,
  });
};
// 更新用户信息
export const updateUserById = (user) => {
  return request.post('/user/updateUserById', user, {
    headers: { 'Content-Type': 'application/json' },
    withCredentials: true
  });
};


// 获取所有用户
export const findAll = () => {
  return request.post('/user/findAll', {}, { withCredentials: true });
};

// 验证密码
export const tryPassword = (admin) => {
  return request.post('/admin/tryPassword', admin, { withCredentials: true });
};

// 添加管理员
export const insertAdmin = (admin) => {
  return request.post('/admin/insertAdmin', admin, { withCredentials: true });
};

// 根据管理员账号获取管理员信息
export const fetchAdminById = (account) => {
  return request.get(`/admin/fetchAdminById`, {
    params: { account }, 
    withCredentials: true,
  });
};

// 更新用户信息
export const updateAdminById = (admin) => {
  return request.post('/admin/updateAdminById', admin, {
    headers: { 'Content-Type': 'application/json' },
    withCredentials: true
  });
};