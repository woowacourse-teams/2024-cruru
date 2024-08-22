import Logo from '@assets/images/logo.svg';
import Accordion from '@components/common/Accordion';
import { Link, useParams } from 'react-router-dom';
import LogoutButton from './LogoutButton';
import S from './style';

interface Option {
  text: string;
  isSelected: boolean;
  postId: number;
}

interface DashboardSidebarProps {
  options: Option[];
}

export default function DashboardSidebar({ options }: DashboardSidebarProps) {
  const { dashboardId } = useParams<{ dashboardId: string }>();

  return (
    <S.Container>
      <Link to={`/dashboard/${dashboardId}/posts`}>
        <S.Logo
          src={Logo}
          alt="크루루 로고"
        />
      </Link>

      <S.Contents>
        <Accordion title={<Link to={`/dashboard/${dashboardId}/posts`}>공고</Link>}>
          {options.map(({ text, isSelected, postId }, index) => (
            // eslint-disable-next-line react/no-array-index-key
            <Accordion.ListItem key={index}>
              <S.LinkContainer isSelected={isSelected}>
                <Link to={`/dashboard/${dashboardId}/${postId}`}>{text}</Link>
              </S.LinkContainer>
            </Accordion.ListItem>
          ))}
        </Accordion>
      </S.Contents>

      <LogoutButton />
    </S.Container>
  );
}
