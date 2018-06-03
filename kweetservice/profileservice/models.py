from django.db import models


# Create your models here.
class Profile(models.Model):
    location = models.TextField(max_length=64, null=True, blank=True)
    contactLink = models.TextField(max_length=64, null=True, blank=True)
    bio = models.TextField(max_length=1024, null=True, blank=True)
    displayName = models.TextField(max_length=64, null=True, blank=True)
    email = models.TextField(max_length=356, null=True, blank=True)
    username = models.TextField(max_length=64, null=True, blank=True)
