// src/components/ClientPopup.js
import React, { useEffect, useState } from 'react';
import { createClient, updateClient } from '../../services/clientService';

const EditClientPopup = ({ isOpen, onClose, client, onClientUpdated }) => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');

    useEffect(() => {
        if (client) {
            setName(client.name);
            setEmail(client.email);
        }
    }, [client]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const clientData = { name, email };

        if (client) {
            await updateClient(client.id, clientData);
        } else {
            await createClient(clientData);
        }

        onClientUpdated(); // Notify parent component
        onClose(); // Close popup
    };

    if (!isOpen) return null;

    return (
        <div className="popup">
            <div className="popup-content">
                <h2>{client ? 'Edit Client' : 'Add Client'}</h2>
                <form onSubmit={handleSubmit}>
                    <label>
                        Name:
                        <input
                            type="text"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                        />
                    </label>
                    <label>
                        Email:
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </label>
                    <button type="submit">Save</button>
                    <button type="button" onClick={onClose}>Cancel</button>
                </form>
            </div>
        </div>
    );
};

export default EditClientPopup;
