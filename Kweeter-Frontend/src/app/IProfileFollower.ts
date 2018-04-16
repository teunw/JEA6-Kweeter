import {IProfile} from './classes/profile';

export interface IProfileFollower {
  id: number;
  profile: IProfile;
  followingProfiles: IProfile[];
}
