import { useEffect, useState } from "react";
import { Reserva } from "../models/ReservaTypes";
import { reservaService } from "../services/reservaService";
import { useAuth } from "../../../shared/contexts/AuthContext";
export function useCheckinViewModel() {
    const [reservasProximas, setReservasProximas] = useState<Reserva[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const { user } = useAuth();
    const clienteId = user?.codigo as string;

    useEffect(() => {
        async function carregarReservas() {
            try {
                const now = new Date();
                const limite = new Date(now.getTime() + 48 * 60 * 60 * 1000);

                const todasReservas = await reservaService.getReservas(clienteId);
                console.log("todas as reservas", todasReservas);

                const proximas = todasReservas.filter((r) => {
                    const dataVoo = new Date(r.voo?.data);
                    return dataVoo > now && dataVoo <= limite;
                });

                console.log("Reservas nas próximas 48h:", proximas);
                setReservasProximas(proximas);
            } catch (e) {
                console.error("Erro ao buscar reservas para check-in:", e);
            } finally {
                setLoading(false);
            }
        }

        carregarReservas();
    }, [clienteId]);


    const fazerCheckin = async (reservaId: string) => {
        try {
            console.log("realizando checkin")
            const reserva: Reserva = await reservaService.atualizarEstadoReserva(reservaId, "CHECK-IN");
            setReservasProximas((prev) =>
                prev.map((r) =>
                    r.codigo === reservaId ? { ...r, estado: "CHECK-IN" } : r
                )
            );
            return reserva
        } catch (e) {
            console.error("Erro ao fazer check-in:", e);
        }
    };

    return {
        reservasProximas,
        loading,
        fazerCheckin,
    };
}
