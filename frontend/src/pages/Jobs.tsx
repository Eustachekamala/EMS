import { useEffect, useState } from 'react';
import toast from 'react-hot-toast';
import { Plus, Trash2, Edit } from 'lucide-react';
import Layout from '@/components/layout/Layout';
import Card from '@/components/common/Card';
import Button from '@/components/common/Button';
import LoadingSpinner from '@/components/common/LoadingSpinner';
import Modal from '@/components/common/Modal';
import Input from '@/components/common/Input';
import { jobService } from '@/services/jobService';
import { departmentService } from '@/services/departmentService';
import type { JobResponse, JobRequest, Department } from '@/types';
import { formatCurrency } from '@/utils/formatters';

export default function Jobs() {
    const [jobs, setJobs] = useState<JobResponse[]>([]);
    const [departments, setDepartments] = useState<Department[]>([]);
    const [loading, setLoading] = useState(true);
    const [deleteId, setDeleteId] = useState<number | null>(null);
    const [deleting, setDeleting] = useState(false);

    // Modal State
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [editingJob, setEditingJob] = useState<JobResponse | null>(null);
    const [formData, setFormData] = useState<JobRequest>({
        title: '',
        description: '',
        salary: 0,
        departmentId: 0
    });

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const [jobsData, deptsData] = await Promise.all([
                jobService.getAllJobs(),
                departmentService.getAllDepartments()
            ]);
            setJobs(jobsData);
            setDepartments(deptsData);
        } catch (error) {
            console.error('Failed to fetch data:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleOpenModal = (job?: JobResponse) => {
        if (job) {
            setEditingJob(job);
            // Find department ID based on name or default
            const dept = departments.find(d => d.name === job.departmentName as string);
            setFormData({
                title: job.title,
                description: job.description,
                salary: job.salary,
                departmentId: dept?.departmentId || 0
            });
        } else {
            setEditingJob(null);
            setFormData({
                title: '',
                description: '',
                salary: 0,
                departmentId: departments[0]?.departmentId || 0
            });
        }
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setEditingJob(null);
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsSubmitting(true);
        const toastId = toast.loading(editingJob ? 'Updating job...' : 'Creating job...');
        try {
            if (editingJob) {
                await jobService.updateJob(editingJob.jobId, formData);
                toast.success('Job position updated successfully!', { id: toastId });
            } else {
                await jobService.createJob(formData);
                toast.success('Job position created successfully!', { id: toastId });
            }
            await fetchData(); // Refresh list
            handleCloseModal();
        } catch (error) {
            console.error('Failed to save job:', error);
            toast.error('Failed to save job position. Please try again.', { id: toastId });
        } finally {
            setIsSubmitting(false);
        }
    };

    const handleDelete = async () => {
        if (!deleteId) return;
        setDeleting(true);
        try {
            await jobService.deleteJob(deleteId);
            setJobs(jobs.filter(j => j.jobId !== deleteId));
            setDeleteId(null);
        } catch (error) {
            console.error('Failed to delete job:', error);
        } finally {
            setDeleting(false);
        }
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: name === 'salary' ? parseFloat(value) || 0 :
                name === 'departmentId' ? parseInt(value) || 0 : value
        }));
    };

    if (loading) {
        return (
            <Layout>
                <div className="flex items-center justify-center h-[60vh]">
                    <LoadingSpinner size="lg" />
                </div>
            </Layout>
        );
    }

    return (
        <Layout>
            <div className="space-y-6 animate-fade-in">
                {/* Header */}
                <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
                    <div>
                        <h1 className="text-3xl font-bold gradient-text">Job Positions</h1>
                        <p className="text-gray-400 mt-1">{jobs.length} positions available</p>
                    </div>
                    <Button onClick={() => handleOpenModal()}>
                        <Plus className="h-5 w-5" />
                        Add Job Position
                    </Button>
                </div>

                {/* Jobs Grid */}
                {jobs.length > 0 ? (
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        {jobs.map((job) => (
                            <Card key={job.jobId} hover>
                                <div className="flex items-start justify-between mb-4">
                                    <div className="flex items-center gap-3">
                                        <div>
                                            <h3 className="font-bold text-white text-lg">{job.title}</h3>
                                            <p className="text-sm text-gray-400 flex items-center gap-1">
                                                {job.departmentName}
                                            </p>
                                        </div>
                                    </div>
                                    <button
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            handleOpenModal(job);
                                        }}
                                        className="p-1.5 rounded-lg bg-white/5 text-gray-400 hover:bg-white/10 hover:text-white transition-colors"
                                    >
                                        <Edit className="h-4 w-4" />
                                    </button>
                                </div>

                                <p className="text-gray-300 text-sm mb-4 line-clamp-2 min-h-[2.5em]">
                                    {job.description || 'No description provided'}
                                </p>

                                <div className="flex items-center justify-between pt-3 border-t border-white/10">
                                    <div className="flex items-center gap-2">
                                        <span className="text-lg font-bold text-emerald-400">
                                            {formatCurrency(job.salary)}
                                        </span>
                                    </div>
                                    <button
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            setDeleteId(job.jobId);
                                        }}
                                        className="p-2 rounded-lg bg-red-600/20 text-red-400 hover:bg-red-600/30 transition-colors"
                                    >
                                        <Trash2 className="h-4 w-4" />
                                    </button>
                                </div>
                            </Card>
                        ))}
                    </div>
                ) : (
                    <Card>
                        <p className="text-gray-400 text-center py-12">No job positions found</p>
                    </Card>
                )}
            </div>

            {/* Create/Edit Modal */}
            <Modal
                isOpen={isModalOpen}
                onClose={handleCloseModal}
                title={editingJob ? 'Edit Job Position' : 'Add New Job Position'}
                size="md"
            >
                <form onSubmit={handleSubmit} className="space-y-4">
                    <Input
                        label="Job Title *"
                        name="title"
                        value={formData.title}
                        onChange={handleInputChange}
                        required
                    />

                    <div>
                        <label className="block text-sm font-medium text-gray-300 mb-1.5">
                            Description
                        </label>
                        <textarea
                            name="description"
                            value={formData.description}
                            onChange={handleInputChange}
                            rows={3}
                            className="w-full px-4 py-2.5 rounded-lg bg-white/5 backdrop-blur-sm border border-white/10 text-gray-100 focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-all duration-200"
                            placeholder="Enter job description..."
                        />
                    </div>

                    <div className="grid grid-cols-2 gap-4">
                        <Input
                            label="Salary *"
                            name="salary"
                            type="number"
                            value={formData.salary}
                            onChange={handleInputChange}
                            required
                        />

                        <div>
                            <label className="block text-sm font-medium text-gray-300 mb-1.5">
                                Department *
                            </label>
                            <select
                                name="departmentId"
                                value={formData.departmentId}
                                onChange={handleInputChange}
                                required
                                className="w-full px-4 py-2.5 rounded-lg bg-white/5 backdrop-blur-sm border border-white/10 text-gray-100 focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-all duration-200"
                            >
                                <option value="">Select Department</option>
                                {departments.map(dept => (
                                    <option key={dept.departmentId} value={dept.departmentId}>
                                        {dept.name}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>

                    <div className="flex gap-3 justify-end pt-4">
                        <Button type="button" variant="secondary" onClick={handleCloseModal}>
                            Cancel
                        </Button>
                        <Button type="submit" isLoading={isSubmitting}>
                            {editingJob ? 'Update Position' : 'Create Position'}
                        </Button>
                    </div>
                </form>
            </Modal>

            {/* Delete Confirmation Modal */}
            <Modal
                isOpen={deleteId !== null}
                onClose={() => setDeleteId(null)}
                title="Delete Job Position"
                size="sm"
            >
                <div className="space-y-4">
                    <p className="text-gray-300">
                        Are you sure you want to delete this job position? This action cannot be undone.
                    </p>
                    <div className="flex gap-3 justify-end">
                        <Button variant="secondary" onClick={() => setDeleteId(null)}>
                            Cancel
                        </Button>
                        <Button variant="danger" onClick={handleDelete} isLoading={deleting}>
                            Delete
                        </Button>
                    </div>
                </div>
            </Modal>
        </Layout>
    );
}
