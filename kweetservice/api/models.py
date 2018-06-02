import uuid

from django.db import models


# Create your models here.
class Kweet(models.Model):
    publicId = models.UUIDField(default=uuid.uuid4())
    textContent = models.TextField(max_length=256)
    author = models.IntegerField()
    date = models.DateTimeField(auto_now=True)
