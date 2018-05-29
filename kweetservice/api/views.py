from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets

from api.models import Kweet
from api.serializers import KweetSerializer


class KweetViewSet(viewsets.ModelViewSet):
    queryset = Kweet.objects.all()
    serializer_class = KweetSerializer