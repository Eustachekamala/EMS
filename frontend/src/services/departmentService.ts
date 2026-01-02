import api from '@/lib/api';
import type { Department } from '@/types';

export const departmentService = {
    getAllDepartments: async (): Promise<Department[]> => {
        const response = await api.get<Department[]>('/departments/all');
        return response.data;
    },
};
