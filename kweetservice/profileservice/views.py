from django_filters.rest_framework import DjangoFilterBackend
from rest_framework import viewsets

# Create your views here.
from profileservice.models import Profile
from profileservice.serializers import ProfileSerializer


class ProfileViewset(viewsets.ModelViewSet):
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer
    filter_backends = (DjangoFilterBackend,)
    filter_fields = ('id', 'location', 'contactLink', 'bio')
