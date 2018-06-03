from rest_framework import serializers

from profileservice.models import Profile


class ProfileSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Profile
        fields = ('url', 'id', 'email', 'username', 'location', 'contactLink', 'bio', 'displayName')
