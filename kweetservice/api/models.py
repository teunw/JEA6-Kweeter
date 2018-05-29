from django.contrib.auth.models import User
from django.db import models


# Create your models here.
class Kweet(models.Model):
    publicId = models.UUIDField()
    textContent = models.TextField(max_length=256)
    author = models.ForeignKey(User, related_name='kweets', on_delete=models.CASCADE)
    date = models.DateTimeField(auto_now=True)
    likedBy = models.ManyToManyField(to=User, related_name='likes')