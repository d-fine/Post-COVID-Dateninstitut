FROM python:3.13-alpine

RUN apk add --no-cache curl

COPY requirements.txt /tmp/requirements.txt
RUN pip install --no-cache-dir -r /tmp/requirements.txt
COPY ./main.py .

ENV PYTHONPATH=.

EXPOSE 8080
