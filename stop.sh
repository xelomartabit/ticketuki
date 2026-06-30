#!/usr/bin/env bash
#
# stop.sh — Detiene los 10 microservicios de Ticketuki (puertos 8001-8010).
#
set -euo pipefail

echo "🛑 Deteniendo microservicios (puertos 8001-8010)..."
any=0
for p in $(seq 8001 8010); do
  pid=$(lsof -ti "tcp:$p" -sTCP:LISTEN 2>/dev/null || true)
  if [ -n "$pid" ]; then
    kill "$pid" 2>/dev/null && echo "   ✅ detenido :$p (PID $pid)"
    any=1
  fi
done
[ "$any" -eq 0 ] && echo "   (no había nada corriendo en 8001-8010)"
echo "Listo."
