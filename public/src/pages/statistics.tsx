import axios from 'axios';
import * as React from 'react';
import { InstagramEmbed, LinkedInEmbed } from 'react-social-media-embed';
import {UserContextProvider, UserContextValue, UserContext} from "../shared/contexts/UserContext"
import Pagination from '../shared/components/pagination';
import Post from '../shared/components/post';
import Box from '@mui/material/Box/Box';
import Tabs from '@mui/material/Tabs/Tabs';
import Tab from '@mui/material/Tab/Tab';
import { PieChart } from '@mui/x-charts';

const Statistics = () => {
    interface TabPanelProps {
        children?: React.ReactNode;
        index: number;
        value: number;
      }

    const [loading, setLoading] = React.useState(false);
    const { isAuth } = React.useContext<UserContextValue>(UserContext);
    const [value, setValue] = React.useState(0);
    const [currentChart, setCurrentChart] = React.useState("percentages");
    const [data, setData] = React.useState<{ label: string; value: number }[]>([]);

    const fetchPerchentages = (async () => {
        setLoading(true)
        setCurrentChart("percentages")
        const response = await axios.get("/statistics/percentages", {params: {token: localStorage.getItem("2w3e8oi9pjuthyf4")}});
        const percentages = response.data.percentages;
        const formattedData = Object.keys(percentages).map(label => ({ label, value: percentages[label] }));
        setData(formattedData);
        const data = await response.data.percentages;
        setLoading(false);
      });

      React.useEffect(() => {
        fetchPerchentages();
      }, []);

      const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
        console.log(newValue)
        setValue(newValue);
        if(newValue == 0 && currentChart != "percentages")
            fetchPerchentages()
      };

      function a11yProps(index: number) {
        return {
          id: `simple-tab-${index}`,
          'aria-controls': `simple-tabpanel-${index}`,
        };
      }

    return (<div>
        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
  <Tabs value={value} onChange={handleTabChange} aria-label="basic tabs example">
    <Tab label="Proportion of post" {...a11yProps(0)} />
    <Tab label="Average reach" {...a11yProps(1)} />
    <Tab label="TODO-2" {...a11yProps(2)} />
    <Tab label="TODO-3" {...a11yProps(3)} />
  </Tabs>
</Box>
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginTop: '5%'}}>
        <PieChart
  series={[
    {data}
  ]}
  width={800}
  height={400}
/>
      </div>
      </div>
      )}
  
  export default Statistics;