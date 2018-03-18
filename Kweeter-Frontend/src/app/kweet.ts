import {IProfile} from './profile';

export interface IKweet {
  publicId: string;
  textContent: string;
  date: Date;
  author: IProfile;
  responses: IKweet[];
  likedBy: IProfile[];
  rekweets: IKweet[];
}

export class Kweet implements IKweet {

  publicId: string = this.kweet.publicId;
  textContent: string = this.kweet.textContent;
  date: Date = this.kweet.date;
  author: IProfile = this.kweet.author;
  responses: IKweet[] = this.kweet.responses;
  likedBy: IProfile[] = this.kweet.likedBy;
  rekweets: IKweet[] = this.kweet.rekweets;

  constructor(public kweet: IKweet) {
  }

  public hasLiked(profile: IProfile) {
    if (!profile) return false;
    return this.kweet.likedBy.map(p => p.id).indexOf(profile.id, 0) != -1;
  }

  public toggleLike(profile:IProfile) {
    const likeIndex = this.kweet.likedBy.map(p => p.id).indexOf(profile.id);
    if (likeIndex != -1) {
      console.log("Slicing...");
      this.kweet.likedBy.splice(likeIndex, 1);
    } else {
      console.log("Adding...");
      this.kweet.likedBy.push(profile);
    }
    return this.kweet.likedBy;
  }
}

export interface IKweetPost {
  textContent: string;
}
