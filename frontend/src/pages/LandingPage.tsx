import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ChevronRight, Shield, Users, BarChart3, Clock, CheckCircle2, Menu, X, ArrowRight } from 'lucide-react';
import Button from '@/components/common/Button';
import toast from 'react-hot-toast';

export default function LandingPage() {
    const navigate = useNavigate();
    const [isScrolled, setIsScrolled] = useState(false);
    const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
    const [newsletterEmail, setNewsletterEmail] = useState('');

    useEffect(() => {
        const handleScroll = () => {
            setIsScrolled(window.scrollY > 10);
        };
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, []);

    const scrollToSection = (id: string) => {
        setMobileMenuOpen(false);
        const element = document.getElementById(id);
        if (element) {
            element.scrollIntoView({ behavior: 'smooth' });
        }
    };

    const handleSubscribe = () => {
        if (!newsletterEmail) {
            toast.error('Please enter a valid email address.');
            return;
        }
        const subject = encodeURIComponent('Subscribe to EMS Pro Newsletter');
        const body = encodeURIComponent(`Please add ${newsletterEmail} to the EMS Pro newsletter list.`);
        // Open the user's default mail client with prefilled recipient, subject and body
        window.location.href = `mailto:eustachekamala.dev@gmail.com?subject=${subject}&body=${body}`;
    }

    return (
        <div className="min-h-screen bg-gray-900 text-white overflow-hidden font-sans selection:bg-primary-500/30">
            {/* Navbar */}
            <nav className={`fixed w-full z-50 transition-all duration-300 ${isScrolled ? 'bg-gray-900/80 backdrop-blur-md border-b border-white/10 py-2' : 'bg-transparent py-4'}`}>
                <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                    <div className="flex items-center justify-between h-16">
                        {/* Logo */}
                        <div className="flex items-center gap-2 cursor-pointer" onClick={() => window.scrollTo({ top: 0, behavior: 'smooth' })}>
                            {/* <div className="w-10 h-10 rounded-xl bg-gradient-to-br from-primary-500 to-indigo-600 flex items-center justify-center font-bold text-xl text-white shadow-lg shadow-primary-500/20">
                                E
                            </div> */}
                            <span className="font-bold text-2xl tracking-tight bg-clip-text text-transparent bg-gradient-to-r from-white to-gray-400">
                                EMS Pro
                            </span>
                        </div>

                        {/* Desktop Navigation */}
                        <div className="hidden md:flex items-center gap-8">
                            <button onClick={() => scrollToSection('features')} className="text-sm font-medium text-gray-300 hover:text-white transition-all hover:scale-105">
                                Features
                            </button>
                            <button onClick={() => scrollToSection('pricing')} className="text-sm font-medium text-gray-300 hover:text-white transition-all hover:scale-105">
                                Pricing
                            </button>
                        </div>

                        {/* Mobile Menu Toggle */}
                        <div className="md:hidden">
                            <button
                                onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
                                className="p-2 rounded-lg text-gray-300 hover:text-white hover:bg-white/10 transition-colors"
                            >
                                {mobileMenuOpen ? <X className="h-6 w-6" /> : <Menu className="h-6 w-6" />}
                            </button>
                        </div>
                    </div>
                </div>

                {/* Mobile Menu Overlay */}
                <div className={`md:hidden absolute top-full left-0 w-full bg-gray-900/95 backdrop-blur-xl border-b border-white/10 transition-all duration-300 ease-in-out origin-top ${mobileMenuOpen ? 'opacity-100 scale-y-100' : 'opacity-0 scale-y-0 h-0'}`}>
                    <div className="px-4 py-8 space-y-4 flex flex-col items-center">
                        <button
                            onClick={() => scrollToSection('features')}
                            className="text-lg font-medium text-gray-300 hover:text-white py-2 w-full text-center hover:bg-white/5 rounded-lg transition-colors"
                        >
                            Features
                        </button>
                        <button
                            onClick={() => scrollToSection('pricing')}
                            className="text-lg font-medium text-gray-300 hover:text-white py-2 w-full text-center hover:bg-white/5 rounded-lg transition-colors"
                        >
                            Pricing
                        </button>
                    </div>
                </div>
            </nav>

            {/* Hero Section */}
            <div className="relative pt-32 pb-20 sm:pt-40 sm:pb-24">
                <div className="absolute top-0 left-1/2 -translate-x-1/2 w-full h-full max-w-7xl pointer-events-none">
                    <div className="absolute top-20 left-1/4 w-72 h-72 bg-primary-500/20 rounded-full blur-3xl mix-blend-screen animate-blob" />
                    <div className="absolute top-20 right-1/4 w-72 h-72 bg-purple-500/20 rounded-full blur-3xl mix-blend-screen animate-blob animation-delay-2000" />
                </div>

                <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10 text-center">
                    <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-white/5 border border-white/10 mb-8 animate-fade-in">
                        <span className="flex h-2 w-2 rounded-full bg-emerald-400 animate-pulse" />
                        <span className="text-sm font-medium text-gray-300">v1.0 Now Available</span>
                    </div>

                    <h1 className="text-5xl md:text-7xl font-bold tracking-tight mb-8 bg-clip-text text-transparent bg-gradient-to-r from-white via-gray-200 to-gray-400 animate-fade-in-up">
                        Manage Your Workforce <br />
                        <span className="text-primary-400">Like a Pro</span>
                    </h1>

                    <p className="max-w-2xl mx-auto text-xl text-gray-400 mb-10 animate-fade-in-up delay-100">
                        Streamline HR operations, track attendance, manage payroll, and boost productivity with our all-in-one Employee Management System.
                    </p>

                    <div className="flex flex-col sm:flex-row items-center justify-center gap-4 animate-fade-in-up delay-200">
                        <Button size="lg" onClick={() => scrollToSection('pricing')} className="min-w-[200px] h-14 text-lg">
                            Get Started
                            <ChevronRight className="ml-2 h-5 w-5" />
                        </Button>
                        <button onClick={() => navigate('/dashboard')} className="px-8 py-4 rounded-xl bg-white/5 text-white font-medium hover:bg-white/10 transition-colors border border-white/10 min-w-[200px]">
                            Live Demo
                        </button>
                    </div>

                    {/* Stats */}
                    <div className="grid grid-cols-2 md:grid-cols-4 gap-8 mt-20 border-t border-white/10 pt-10">
                        {[
                            { label: 'Active Users', value: '2k+' },
                            { label: 'Companies', value: '50+' },
                            { label: 'Uptime', value: '99.9%' },
                            { label: 'Support', value: '24/7' },
                        ].map((stat, i) => (
                            <div key={i}>
                                <div className="text-3xl font-bold text-white mb-1">{stat.value}</div>
                                <div className="text-sm text-gray-400">{stat.label}</div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>

            {/* Features Grid */}
            <div id="features" className="py-24 bg-gray-900/50">
                <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                    <div className="text-center mb-16">
                        <h2 className="text-3xl md:text-4xl font-bold mb-4">Everything you need</h2>
                        <p className="text-gray-400 max-w-2xl mx-auto">
                            Powerful features designed to help you manage your team effectively and efficiently.
                        </p>
                    </div>

                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                        {[
                            {
                                icon: Users,
                                title: 'Employee Management',
                                desc: 'Complete profile management with detailed records and history.',
                                color: 'text-blue-400'
                            },
                            {
                                icon: Clock,
                                title: 'Attendance Tracking',
                                desc: 'Real-time RFID attendance tracking with automated reporting.',
                                color: 'text-emerald-400'
                            },
                            {
                                icon: Shield,
                                title: 'Secure Access',
                                desc: 'Role-based access control and encrypted data protection.',
                                color: 'text-purple-400'
                            },
                            {
                                icon: BarChart3,
                                title: 'Analytics & Reports',
                                desc: 'Deep insights into workforce productivity and trends.',
                                color: 'text-amber-400'
                            },
                            {
                                icon: CheckCircle2,
                                title: 'Payroll Integration',
                                desc: 'Automated salary calculations and payslip generation.',
                                color: 'text-rose-400'
                            },
                            {
                                icon: Shield,
                                title: 'Compliance Ready',
                                desc: 'Built-in features to help you stay compliant with labor laws.',
                                color: 'text-cyan-400'
                            }
                        ].map((feature, i) => (
                            <div key={i} className="p-8 rounded-2xl bg-white/5 border border-white/10 hover:bg-white/[0.07] transition-colors group">
                                <div className={`w-12 h-12 rounded-xl bg-white/5 flex items-center justify-center mb-6 group-hover:scale-110 transition-transform ${feature.color}`}>
                                    <feature.icon className="h-6 w-6" />
                                </div>
                                <h3 className="text-xl font-bold mb-3">{feature.title}</h3>
                                <p className="text-gray-400 leading-relaxed">
                                    {feature.desc}
                                </p>
                            </div>
                        ))}
                    </div>
                </div>
            </div>

            {/* Pricing Section */}
            <div id="pricing" className="py-24 relative overflow-hidden">
                <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10">
                    <div className="text-center mb-16">
                        <h2 className="text-3xl md:text-4xl font-bold mb-4">Simple, Transparent Pricing</h2>
                        <p className="text-gray-400 max-w-2xl mx-auto">
                            Choose the plan that fits your business needs. No hidden fees.
                        </p>
                    </div>

                    <div className="grid grid-cols-1 md:grid-cols-3 gap-8 max-w-5xl mx-auto">
                        {/* Starter Plan */}
                        <div className="p-8 rounded-2xl bg-white/5 border border-white/10 hover:border-primary-500/50 transition-colors relative group">
                            <h3 className="text-xl font-bold mb-2">Starter</h3>
                            <div className="flex items-baseline gap-1 mb-6">
                                <span className="text-4xl font-bold">$29</span>
                                <span className="text-gray-400">/mo</span>
                            </div>
                            <ul className="space-y-4 mb-8 text-gray-300">
                                <li className="flex items-center gap-3">
                                    <CheckCircle2 className="h-5 w-5 text-primary-400" />
                                    Up to 10 Employees
                                </li>
                                <li className="flex items-center gap-3">
                                    <CheckCircle2 className="h-5 w-5 text-primary-400" />
                                    Basic Attendance
                                </li>
                                <li className="flex items-center gap-3">
                                    <CheckCircle2 className="h-5 w-5 text-primary-400" />
                                    Standard Support
                                </li>
                            </ul>
                            <Button className="w-full" variant="secondary" onClick={() => navigate('/payment?plan=starter')}>Choose Plan</Button>
                        </div>

                        {/* Pro Plan */}
                        <div className="p-8 rounded-2xl bg-primary-600/20 border border-primary-500 hover:shadow-lg hover:shadow-primary-500/20 transition-all relative transform md:-translate-y-4">
                            <div className="absolute top-0 right-0 bg-primary-500 text-white text-xs font-bold px-3 py-1 rounded-bl-lg rounded-tr-lg">POPULAR</div>
                            <h3 className="text-xl font-bold mb-2">Pro</h3>
                            <div className="flex items-baseline gap-1 mb-6">
                                <span className="text-4xl font-bold text-white">$99</span>
                                <span className="text-gray-400">/mo</span>
                            </div>
                            <ul className="space-y-4 mb-8 text-white">
                                <li className="flex items-center gap-3">
                                    <CheckCircle2 className="h-5 w-5 text-emerald-400" />
                                    Up to 50 Employees
                                </li>
                                <li className="flex items-center gap-3">
                                    <CheckCircle2 className="h-5 w-5 text-emerald-400" />
                                    Advanced Analytics
                                </li>
                                <li className="flex items-center gap-3">
                                    <CheckCircle2 className="h-5 w-5 text-emerald-400" />
                                    Payroll Management
                                </li>
                                <li className="flex items-center gap-3">
                                    <CheckCircle2 className="h-5 w-5 text-emerald-400" />
                                    Priority Support
                                </li>
                            </ul>
                            <Button className="w-full" onClick={() => navigate('/payment?plan=pro')}>Choose Plan</Button>
                        </div>

                        {/* Enterprise Plan */}
                        <div className="p-8 rounded-2xl bg-white/5 border border-white/10 hover:border-primary-500/50 transition-colors relative group">
                            <h3 className="text-xl font-bold mb-2">Enterprise</h3>
                            <div className="flex items-baseline gap-1 mb-6">
                                <span className="text-4xl font-bold">$299</span>
                                <span className="text-gray-400">/mo</span>
                            </div>
                            <ul className="space-y-4 mb-8 text-gray-300">
                                <li className="flex items-center gap-3">
                                    <CheckCircle2 className="h-5 w-5 text-primary-400" />
                                    Unlimited Employees
                                </li>
                                <li className="flex items-center gap-3">
                                    <CheckCircle2 className="h-5 w-5 text-primary-400" />
                                    Custom Integrations
                                </li>
                                <li className="flex items-center gap-3">
                                    <CheckCircle2 className="h-5 w-5 text-primary-400" />
                                    Dedicated Account Manager
                                </li>
                            </ul>
                            <Button className="w-full" variant="secondary" onClick={() => navigate('/payment?plan=enterprise')}>Contact Sales</Button>
                        </div>
                    </div>
                </div>
            </div>

            {/* Footer */}
            <footer className="border-t border-white/10 bg-gray-900 pt-16 pb-8">
                <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-12 mb-12">
                        {/* Brand Column */}
                        <div className="space-y-4">
                            <div className="flex items-center gap-2">
                                <span className="font-bold text-xl tracking-tight text-white">
                                    EMS Pro
                                </span>
                            </div>
                            <p className="text-gray-400 text-sm leading-relaxed">
                                Streamlining workforce management for modern enterprises. Built for scale, security, and simplicity.
                            </p>
                        </div>

                        {/* Product Column */}
                        <div>
                            <h4 className="font-bold text-white mb-6">Product</h4>
                            <ul className="space-y-3 text-sm text-gray-400">
                                {['Features', 'Pricing', 'Integrations'].map((item) => (
                                    <li key={item}>
                                        <a href="#" className="hover:text-primary-400 transition-colors">{item}</a>
                                    </li>
                                ))}
                            </ul>
                        </div>

                        {/* Newsletter Column */}
                        <div>
                            <h4 className="font-bold text-white mb-6">Stay Updated</h4>
                            <p className="text-gray-400 text-sm mb-4">
                                Subscribe to our newsletter for the latest updates and features.
                            </p>
                            <div className="flex gap-2">
                                <input
                                    type="email"
                                    placeholder="Enter your email"
                                    value={newsletterEmail}
                                    onChange={(e) => setNewsletterEmail(e.target.value)}
                                    className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 text-sm text-white focus:outline-none focus:border-primary-500 focus:ring-1 focus:ring-primary-500 w-full"
                                />
                                <button onClick={handleSubscribe} className="p-2 rounded-lg bg-primary-600 hover:bg-primary-500 text-white transition-colors">
                                    <ArrowRight className="h-4 w-4" />
                                </button>
                            </div>
                        </div>
                    </div>

                    <div className="pt-8 border-t border-white/10 flex flex-col md:flex-row justify-between items-center gap-4">
                        <p className="text-gray-500 text-sm">
                            &copy; 2026 EMS Pro. All rights reserved.
                        </p>
                    </div>
                </div>
            </footer>
        </div>
    );
}
