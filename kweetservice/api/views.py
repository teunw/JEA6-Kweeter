# Create your views here.
from django_filters.rest_framework import DjangoFilterBackend
from rest_framework import viewsets

from api.models import Kweet
from api.serializers import KweetSerializer


class KweetViewSet(viewsets.ModelViewSet):
    queryset = Kweet.objects.all()
    serializer_class = KweetSerializer
    filter_backends = (DjangoFilterBackend,)
    filter_fields = ('publicId', 'textContent', 'author', 'date')
