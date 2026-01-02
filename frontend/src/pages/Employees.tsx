import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Plus, Search, Mail, Phone, Briefcase, Trash2, Edit } from 'lucide-react';
import Layout from '@/components/layout/Layout';
import Card from '@/components/common/Card';
import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import LoadingSpinner from '@/components/common/LoadingSpinner';
import Modal from '@/components/common/Modal';
import { employeeService } from '@/services/employeeService';
import type { EmployeeResponse } from '@/types';
import { formatDate, formatName, getInitials } from '@/utils/formatters';

export default function Employees() {
    const [employees, setEmployees] = useState<EmployeeResponse[]>([]);
    const [filteredEmployees, setFilteredEmployees] = useState<EmployeeResponse[]>([]);
    const [loading, setLoading] = useState(true);
    const [searchQuery, setSearchQuery] = useState('');
    const [deleteId, setDeleteId] = useState<number | null>(null);
    const [deleting, setDeleting] = useState(false);

    useEffect(() => {
        fetchEmployees();
    }, []);

    useEffect(() => {
        const filtered = employees.filter(emp =>
            `${emp.firstname} ${emp.lastname}`.toLowerCase().includes(searchQuery.toLowerCase()) ||
            emp.email.toLowerCase().includes(searchQuery.toLowerCase())
        );
        setFilteredEmployees(filtered);
    }, [searchQuery, employees]);

    const fetchEmployees = async () => {
        try {
            const data = await employeeService.getAllEmployees();
            setEmployees(data);
            setFilteredEmployees(data);
        } catch (error) {
            console.error('Failed to fetch employees:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async () => {
        if (!deleteId) return;
        setDeleting(true);
        try {
            await employeeService.deleteEmployee(deleteId);
            setEmployees(employees.filter(e => e.id !== deleteId));
            setDeleteId(null);
        } catch (error) {
            console.error('Failed to delete employee:', error);
        } finally {
            setDeleting(false);
        }
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
                        <h1 className="text-3xl font-bold gradient-text">Employees</h1>
                        <p className="text-gray-400 mt-1">{employees.length} total employees</p>
                    </div>
                    <Link to="/employees/new">
                        <Button>
                            <Plus className="h-5 w-5" />
                            Add Employee
                        </Button>
                    </Link>
                </div>

                {/* Search */}
                <Card>
                    <div className="relative">
                        <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400" />
                        <Input
                            type="text"
                            placeholder="Search employees by name or email..."
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                            className="pl-10"
                        />
                    </div>
                </Card>

                {/* Employee Grid */}
                {filteredEmployees.length > 0 ? (
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        {filteredEmployees.map((employee) => (
                            <Card key={employee.id} hover>
                                <div className="flex items-start justify-between mb-4">
                                    <div className="flex items-center gap-3">
                                        <div className="w-12 h-12 rounded-full bg-gradient-to-br from-primary-500 to-primary-700 flex items-center justify-center text-white font-bold shadow-lg">
                                            {getInitials(employee.firstname, employee.lastname)}
                                        </div>
                                        <div>
                                            <h3 className="font-bold text-white text-lg">
                                                {formatName(employee.firstname, employee.lastname)}
                                            </h3>
                                            <p className="text-sm text-gray-400">{employee.jobTitle || 'No Position'}</p>
                                        </div>
                                    </div>
                                </div>

                                <div className="space-y-2 mb-4">
                                    <div className="flex items-center gap-2 text-gray-300 text-sm">
                                        <Mail className="h-4 w-4 text-gray-400" />
                                        <span className="truncate">{employee.email}</span>
                                    </div>
                                    <div className="flex items-center gap-2 text-gray-300 text-sm">
                                        <Phone className="h-4 w-4 text-gray-400" />
                                        <span>{employee.phone}</span>
                                    </div>
                                    {employee.departmentName && (
                                        <div className="flex items-center gap-2 text-gray-300 text-sm">
                                            <Briefcase className="h-4 w-4 text-gray-400" />
                                            <span>{employee.departmentName}</span>
                                        </div>
                                    )}
                                </div>

                                <div className="flex items-center justify-between pt-3 border-t border-white/10">
                                    <span className="text-xs text-gray-400">
                                        Joined {formatDate(employee.hireDate)}
                                    </span>
                                    <div className="flex gap-2">
                                        <Link to={`/employees/${employee.id}`}>
                                            <button className="p-2 rounded-lg bg-primary-600/20 text-primary-400 hover:bg-primary-600/30 transition-colors">
                                                <Edit className="h-4 w-4" />
                                            </button>
                                        </Link>
                                        <button
                                            onClick={() => setDeleteId(employee.id)}
                                            className="p-2 rounded-lg bg-red-600/20 text-red-400 hover:bg-red-600/30 transition-colors"
                                        >
                                            <Trash2 className="h-4 w-4" />
                                        </button>
                                    </div>
                                </div>
                            </Card>
                        ))}
                    </div>
                ) : (
                    <Card>
                        <p className="text-gray-400 text-center py-12">No employees found</p>
                    </Card>
                )}
            </div>

            {/* Delete Confirmation Modal */}
            <Modal
                isOpen={deleteId !== null}
                onClose={() => setDeleteId(null)}
                title="Delete Employee"
                size="sm"
            >
                <div className="space-y-4">
                    <p className="text-gray-300">
                        Are you sure you want to delete this employee? This action cannot be undone.
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
