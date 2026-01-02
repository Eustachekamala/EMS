import { useState, useEffect } from 'react';
import toast from 'react-hot-toast';
import { useNavigate, useParams } from 'react-router-dom';
import { ArrowLeft, User, Mail, MapPin, Briefcase } from 'lucide-react';
import Layout from '@/components/layout/Layout';
import Card from '@/components/common/Card';
import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import { employeeService } from '@/services/employeeService';
import { departmentService } from '@/services/departmentService';
import { jobService } from '@/services/jobService';
import type { EmployeeRequest, Department, JobResponse } from '@/types';

export default function NewEmployee() {
    const navigate = useNavigate();
    const { id } = useParams();
    const isEditing = !!id;

    const [loading, setLoading] = useState(false);
    const [departments, setDepartments] = useState<Department[]>([]);
    const [jobs, setJobs] = useState<JobResponse[]>([]);

    const [formData, setFormData] = useState<EmployeeRequest>({
        firstname: '',
        lastname: '',
        email: '',
        password: '',
        phone: '',
        hireDate: new Date().toISOString().split('T')[0],
        dob: '',
        gender: 'Male',
        street: '',
        city: '',
        country: '',
        zipcode: '',
        rfidTag: '',
        dailyRate: 0,
        departmentId: undefined,
        jobId: undefined
    });

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [deptData, jobData] = await Promise.all([
                    departmentService.getAllDepartments(),
                    jobService.getAllJobs()
                ]);
                setDepartments(deptData);
                setJobs(jobData);

                if (isEditing && id) {
                    const employee = await employeeService.getEmployeeById(parseInt(id));
                    setFormData({
                        firstname: employee.firstname,
                        lastname: employee.lastname,
                        email: employee.email,
                        password: '', // Password not normally sent back, leave empty or handle separately
                        phone: employee.phone,
                        hireDate: employee.hireDate,
                        dob: employee.birthDate,
                        gender: employee.gender,
                        street: employee.street,
                        city: employee.city,
                        country: employee.country,
                        zipcode: employee.zipcode,
                        rfidTag: employee.rfidTag || '',
                        dailyRate: employee.dailyRate,
                        departmentId: employee.departmentId,
                        jobId: employee.jobId
                    });
                }
            } catch (error) {
                console.error('Failed to fetch data', error);
                if (isEditing) {
                    toast.error('Failed to load employee details');
                    navigate('/employees');
                }
            }
        };
        fetchData();
    }, [id, isEditing, navigate]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        const toastId = toast.loading(isEditing ? 'Updating employee...' : 'Creating employee...');
        try {
            if (isEditing && id) {
                // Update
                const updateData = { ...formData };
                // Don't send empty password on update if it wasn't changed
                if (!updateData.password) delete updateData.password;

                await employeeService.updateEmployee(parseInt(id), updateData);
                toast.success('Employee updated successfully!', { id: toastId });
            } else {
                // Create
                await employeeService.createEmployee(formData);
                toast.success('Employee created successfully!', { id: toastId });
            }
            navigate('/employees');
        } catch (error) {
            console.error('Failed to save employee:', error);
            toast.error('Failed to save employee. Please try again.', { id: toastId });
        } finally {
            setLoading(false);
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;

        setFormData(prev => {
            const updates: Partial<EmployeeRequest> = { [name]: value };

            if (name === 'dailyRate') {
                updates.dailyRate = parseFloat(value) || 0;
            } else if (name === 'departmentId' || name === 'jobId') {
                updates[name] = parseInt(value) || undefined;
            }

            // Logic for linked dropdowns
            if (name === 'jobId') {
                const selectedJobId = parseInt(value);
                const selectedJob = jobs.find(j => j.jobId === selectedJobId);
                if (selectedJob) {
                    updates.departmentId = selectedJob.departmentId;
                    // Optional: Auto-calculate daily rate visualization if needed, but we keep the manual override
                    // updates.dailyRate = parseFloat((selectedJob.salary / 30).toFixed(2));
                }
            } else if (name === 'departmentId') {
                const newDeptId = parseInt(value);
                // If current job doesn't match new department, clear job
                const currentJob = jobs.find(j => j.jobId === prev.jobId);
                if (currentJob && currentJob.departmentId !== newDeptId) {
                    updates.jobId = undefined;
                }
            }

            return { ...prev, ...updates };
        });
    };

    return (
        <Layout>
            <div className="max-w-3xl mx-auto space-y-6 animate-fade-in">
                {/* Header */}
                <div className="flex items-center gap-4">
                    <Button variant="ghost" onClick={() => navigate('/employees')}>
                        <ArrowLeft className="h-5 w-5" />
                    </Button>
                    <div>
                        <h1 className="text-3xl font-bold gradient-text">{isEditing ? 'Edit Employee' : 'Add New Employee'}</h1>
                        <p className="text-gray-400 mt-1">{isEditing ? 'Update employee information' : 'Create a new employee profile'}</p>
                    </div>
                </div>

                {/* Form */}
                <form onSubmit={handleSubmit}>
                    <Card>
                        <div className="space-y-6">
                            {/* Personal Information */}
                            <div>
                                <h2 className="text-xl font-semibold text-white mb-4 flex items-center gap-2">
                                    <User className="h-5 w-5" />
                                    Personal Information
                                </h2>
                                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                    <Input
                                        label="First Name *"
                                        name="firstname"
                                        value={formData.firstname}
                                        onChange={handleChange}
                                        required
                                    />
                                    <Input
                                        label="Last Name *"
                                        name="lastname"
                                        value={formData.lastname}
                                        onChange={handleChange}
                                        required
                                    />
                                    <Input
                                        label="Birth Date *"
                                        name="dob"
                                        type="date"
                                        value={formData.dob}
                                        onChange={handleChange}
                                        required
                                    />
                                    <div>
                                        <label className="block text-sm font-medium text-gray-300 mb-1.5">
                                            Gender *
                                        </label>
                                        <select
                                            name="gender"
                                            value={formData.gender}
                                            onChange={handleChange}
                                            required
                                            className="w-full px-4 py-2.5 rounded-lg bg-white/5 backdrop-blur-sm border border-white/10 text-gray-100 focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-all duration-200"
                                        >
                                            <option value="Male">Male</option>
                                            <option value="Female">Female</option>
                                            <option value="Other">Other</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            {/* Contact Information */}
                            <div className="pt-6 border-t border-white/10">
                                <h2 className="text-xl font-semibold text-white mb-4 flex items-center gap-2">
                                    <Mail className="h-5 w-5" />
                                    Contact Information
                                </h2>
                                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                    <Input
                                        label="Email *"
                                        name="email"
                                        type="email"
                                        value={formData.email}
                                        onChange={handleChange}
                                        required
                                    />
                                    <Input
                                        label="Phone *"
                                        name="phone"
                                        type="tel"
                                        value={formData.phone}
                                        onChange={handleChange}
                                        required
                                    />
                                    <Input
                                        label={isEditing ? "Password (leave blank to keep)" : "Password *"}
                                        name="password"
                                        type="password"
                                        value={formData.password}
                                        onChange={handleChange}
                                        required={!isEditing}
                                    />
                                    <Input
                                        label="RFID Tag"
                                        name="rfidTag"
                                        value={formData.rfidTag}
                                        onChange={handleChange}
                                    />
                                </div>
                            </div>

                            {/* Address Information */}
                            <div className="pt-6 border-t border-white/10">
                                <h2 className="text-xl font-semibold text-white mb-4 flex items-center gap-2">
                                    <MapPin className="h-5 w-5" />
                                    Address
                                </h2>
                                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                    <Input
                                        label="Street"
                                        name="street"
                                        value={formData.street}
                                        onChange={handleChange}
                                    />
                                    <Input
                                        label="City"
                                        name="city"
                                        value={formData.city}
                                        onChange={handleChange}
                                    />
                                    <Input
                                        label="Country"
                                        name="country"
                                        value={formData.country}
                                        onChange={handleChange}
                                    />
                                    <Input
                                        label="Zipcode"
                                        name="zipcode"
                                        value={formData.zipcode}
                                        onChange={handleChange}
                                    />
                                </div>
                            </div>

                            {/* Employment Information */}
                            <div className="pt-6 border-t border-white/10">
                                <h2 className="text-xl font-semibold text-white mb-4 flex items-center gap-2">
                                    <Briefcase className="h-5 w-5" />
                                    Employment Information
                                </h2>
                                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                    <Input
                                        label="Hire Date *"
                                        name="hireDate"
                                        type="date"
                                        value={formData.hireDate}
                                        onChange={handleChange}
                                        required
                                    />
                                    <Input
                                        label="Daily Rate *"
                                        name="dailyRate"
                                        type="number"
                                        step="0.01"
                                        value={formData.dailyRate}
                                        onChange={handleChange}
                                        required
                                    />
                                    <div>
                                        <label className="block text-sm font-medium text-gray-300 mb-1.5">
                                            Department
                                        </label>
                                        <select
                                            name="departmentId"
                                            value={formData.departmentId || ''}
                                            onChange={handleChange}
                                            className="w-full px-4 py-2.5 rounded-lg bg-white/5 backdrop-blur-sm border border-white/10 text-gray-100 focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-all duration-200"
                                        >
                                            <option value="">Select Department</option>
                                            {departments.map((dept) => (
                                                <option key={dept.departmentId} value={dept.departmentId}>
                                                    {dept.name}
                                                </option>
                                            ))}
                                        </select>
                                    </div>
                                    <div>
                                        <label className="block text-sm font-medium text-gray-300 mb-1.5">
                                            Job Position
                                        </label>
                                        <select
                                            name="jobId"
                                            value={formData.jobId || ''}
                                            onChange={handleChange}
                                            className="w-full px-4 py-2.5 rounded-lg bg-white/5 backdrop-blur-sm border border-white/10 text-gray-100 focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-all duration-200"
                                        >
                                            <option value="">Select Job</option>
                                            {jobs
                                                .filter(job => !formData.departmentId || job.departmentId === formData.departmentId)
                                                .map((job) => (
                                                    <option key={job.jobId} value={job.jobId}>
                                                        {job.title}
                                                    </option>
                                                ))}
                                        </select>
                                    </div>
                                </div>
                            </div>

                            {/* Actions */}
                            <div className="flex gap-3 justify-end pt-6 border-t border-white/10">
                                <Button type="button" variant="secondary" onClick={() => navigate('/employees')}>
                                    Cancel
                                </Button>
                                <Button type="submit" isLoading={loading}>
                                    {isEditing ? 'Update Employee' : 'Create Employee'}
                                </Button>
                            </div>
                        </div>
                    </Card>
                </form>
            </div>
        </Layout>
    );
}
