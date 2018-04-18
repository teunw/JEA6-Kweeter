import {IProfile} from './profile';
import {ProfileService} from '../services/profile.service';

export interface IKweet {
  publicId: string;
  textContent: string;
  date: Date;
  author: string;
  responses: IKweet[];
  likedBy: string[];
  rekweets: IKweet[];
}

export class Kweet {

  private likedBy: IProfile[] = [];

  constructor(public serverData: IKweet, private profileService: ProfileService) {
  }

  public async getAuthor(): Promise<IProfile> {
    return this.profileService.getProfileByUrl(this.serverData.author);
  }

  public async getLikedBy(): Promise<IProfile[]> {
    if (this.likedBy.length > 0) {
      return await this.likedBy;
    }
    const serverLikedBy = this.serverData.likedBy;
    const mappedLikes = serverLikedBy
      .map(async (url: string) => {
        return await this.profileService.getProfileByUrl(url);
      });
    this.likedBy = await Promise.all(mappedLikes) as IProfile[];
    return this.likedBy;
  }

  public async hasLiked(profile: IProfile) {
    if (!profile) {
      return false;
    }
    return this.likedBy.map(p => p.id).indexOf(profile.id, 0) !== -1;
  }

  public async toggleLike(profile: IProfile): Promise<boolean> {
    const likeIndex = (await this.getLikedBy()).map(p => p.id).indexOf(profile.id);
    if (likeIndex !== -1) {
      console.log('Slicing...');
      this.serverData.likedBy.splice(likeIndex, 1);
    } else {
      console.log('Adding...');
      this.likedBy.push(profile);
    }
    return likeIndex === -1;
  }
}

export interface IKweetPost {
  textContent: string;
}
