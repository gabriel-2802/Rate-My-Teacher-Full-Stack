import api from './api';

export const getAllTeachers = async () => {
  const response = await api.get('/teacher');
  return response.data;
};

export const searchTeachers = async (query) => {
  const response = await api.get(`/teacher?search=${encodeURIComponent(query)}`);
  return response.data;
};


export const searchTeachersByUniversity = async (universityName) => {
  const response = await api.get(`/teachers/search?university=${encodeURIComponent(universityName)}`);
  return response.data;
};
