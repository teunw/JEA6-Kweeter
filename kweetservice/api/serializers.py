from rest_framework import serializers

from api.models import Kweet


class KweetSerializer(serializers.ModelSerializer):
    class Meta:
        model = Kweet
        fields = ('url', 'id', 'publicId', 'textContent', 'author', 'date', 'likedBy')