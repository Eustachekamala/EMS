import api from '@/lib/api';
import type { JobResponse, JobRequest } from '@/types';

export const jobService = {
    async getAllJobs(): Promise<JobResponse[]> {
        const response = await api.get('/jobs/all');
        return response.data;
    },

    async getJobById(id: number): Promise<JobResponse> {
        const response = await api.get(`/jobs/get/${id}`);
        return response.data;
    },

    async getJobByTitle(title: string): Promise<JobResponse> {
        const response = await api.get(`/jobs/${title}`);
        return response.data;
    },

    async createJob(job: JobRequest): Promise<string> {
        const response = await api.post('/jobs/create', job);
        return response.data;
    },

    async updateJob(id: number, job: JobRequest): Promise<string> {
        const response = await api.patch(`/jobs/update/${id}`, job);
        return response.data;
    },

    async deleteJob(id: number): Promise<string> {
        const response = await api.delete(`/jobs/delete/${id}`);
        return response.data;
    },
};
