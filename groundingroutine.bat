@echo off
title grounding routine
cd /d %~dp0
cd C:/ga/
ga-server-event-driven config/server.neverball.conf >> Neverball.log 2>&1
waitfor WaitForServerToBeReady /t 5
 ga-client config/client.abs.conf rtsp://localhost:8554/desktop