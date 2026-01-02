import { useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { CreditCard, Shield, CheckCircle2 } from 'lucide-react';
import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import toast from 'react-hot-toast';
import { useUIStore } from '@/store/uiStore';

export default function Payment() {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const plan = searchParams.get('plan') || 'pro';
    const { setEnterpriseName } = useUIStore();

    const [loading, setLoading] = useState(false);
    const [formData, setFormData] = useState({
        companyName: '',
        email: '',
        cardNumber: '',
        expiry: '',
        cvc: '',
        nameOnCard: ''
    });

    const prices = {
        starter: 29,
        pro: 99,
        enterprise: 299
    };

    const currentPrice = prices[plan as keyof typeof prices] || 99;

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);

        const toastId = toast.loading('Processing payment...');

        // Simulate payment processing
        setTimeout(() => {
            setEnterpriseName(formData.companyName);
            toast.success('Payment successful! Setting up your workspace...', { id: toastId });

            setTimeout(() => {
                navigate('/dashboard');
            }, 1000);
            setLoading(false);
        }, 2000);
    };

    return (
        <div className="min-h-screen bg-gray-900 text-white py-12 px-4 sm:px-6 lg:px-8 flex items-center justify-center">
            <div className="max-w-4xl w-full grid grid-cols-1 md:grid-cols-2 gap-8">

                {/* Order Summary */}
                <div className="space-y-6">
                    <div>
                        <h2 className="text-3xl font-bold gradient-text mb-2">Complete Your Purchase</h2>
                        <p className="text-gray-400">Unlock the full power of EMS Pro for your organization.</p>
                    </div>

                    <div className="bg-white/5 border border-white/10 rounded-2xl p-6 space-y-4">
                        <div className="flex justify-between items-center pb-4 border-b border-white/10">
                            <div>
                                <h3 className="font-bold text-lg capitalize">{plan} Plan</h3>
                                <p className="text-sm text-gray-400">Monthly subscription</p>
                            </div>
                            <span className="text-2xl font-bold">${currentPrice}</span>
                        </div>

                        <ul className="space-y-3">
                            <li className="flex items-center gap-3 text-gray-300">
                                <CheckCircle2 className="h-5 w-5 text-emerald-400" />
                                Instant activation
                            </li>
                            <li className="flex items-center gap-3 text-gray-300">
                                <CheckCircle2 className="h-5 w-5 text-emerald-400" />
                                Secure SSL payment
                            </li>
                            <li className="flex items-center gap-3 text-gray-300">
                                <CheckCircle2 className="h-5 w-5 text-emerald-400" />
                                30-day money-back guarantee
                            </li>
                        </ul>
                    </div>

                    <div className="bg-primary-600/10 border border-primary-500/20 rounded-xl p-4 flex gap-4 items-start">
                        <Shield className="h-6 w-6 text-primary-400 shrink-0" />
                        <div>
                            <h4 className="font-bold text-sm text-primary-400 mb-1">Secure Transaction</h4>
                            <p className="text-xs text-gray-400">Your payment information is encrypted and secure. We never store your full card details.</p>
                        </div>
                    </div>
                </div>

                {/* Payment Form */}
                <div className="bg-white/5 backdrop-blur-sm border border-white/10 rounded-2xl p-6 md:p-8">
                    <form onSubmit={handleSubmit} className="space-y-6">
                        <div>
                            <h3 className="text-xl font-bold mb-4 flex items-center gap-2">
                                Company Details
                            </h3>
                            <div className="space-y-4">
                                <Input
                                    label="Company Name *"
                                    name="companyName"
                                    placeholder="e.g. John Doe Inc."
                                    value={formData.companyName}
                                    onChange={handleInputChange}
                                    required
                                />
                                <Input
                                    label="Work Email *"
                                    name="email"
                                    type="email"
                                    placeholder="ems@company.com"
                                    value={formData.email}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>
                        </div>

                        <div className="pt-4 border-t border-white/10">
                            <h3 className="text-xl font-bold mb-4 flex items-center gap-2">
                                <CreditCard className="h-5 w-5 text-primary-400" />
                                Payment Method
                            </h3>
                            <div className="space-y-4">
                                <Input
                                    label="Name on Card *"
                                    name="nameOnCard"
                                    value={formData.nameOnCard}
                                    onChange={handleInputChange}
                                    required
                                />
                                <Input
                                    label="Card Number *"
                                    name="cardNumber"
                                    placeholder="0000 0000 0000 0000"
                                    value={formData.cardNumber}
                                    onChange={handleInputChange}
                                    required
                                />
                                <div className="grid grid-cols-2 gap-4">
                                    <Input
                                        label="Expiry *"
                                        name="expiry"
                                        placeholder="MM/YY"
                                        value={formData.expiry}
                                        onChange={handleInputChange}
                                        required
                                    />
                                    <Input
                                        label="CVC *"
                                        name="cvc"
                                        placeholder="123"
                                        value={formData.cvc}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </div>
                            </div>
                        </div>

                        <Button type="submit" className="w-full h-12 text-lg mt-6" isLoading={loading}>
                            Pay ${currentPrice} & Launch
                        </Button>
                    </form>
                </div>
            </div>
        </div>
    );
}
