import axios from "axios";

const request = axios.create({
  timeout: 5000,
});

// 上传图片接口
export const uploadImage = async (file) => {
  const formData = new FormData();
  formData.append('file', file);

  try {
    const response = await request.post(
      '/upload',
      formData,
      {
        headers: {
          Authorization: `Bearer 24|n8Yc4TN1C5xegETmEULES2W4vcl8PMVFI4XFWxrQ`,
          Accept: 'application/json',
          'Content-Type': 'multipart/form-data',
        },
      }
    );
    return response.data.data.links.url;
  } catch (error) {
    console.error('上传失败:', error);
    throw error;
  }
};

